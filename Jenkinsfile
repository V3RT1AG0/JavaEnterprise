pipeline {
	agent any
	environment {
		img = ''
	}
	tools {
	  maven 'maven_3.6.2'
	}
	
    stages {
        
        stage('MavenBuild') {
		steps{
		
              
             	 sh 'mvn -version'
	
		 sh 'mvn clean install'
		
		sh 'jar -tvf target/surveyForm-1.war'
			
		}
        }
	    
	    stage("BuildPublishImage"){
			steps {
				script {
					echo "${env.BUILD_ID}"
					img = docker.build "parnavi/img-kitty:${env.BUILD_ID}"

					withDockerRegistry(credentialsId: 'docker', url: '') {
						echo "Creating docker image and pusing to docker hub ..."

						img.push "${env.BUILD_ID}"
					}
				}
			}
		}

		stage("UpdateDeployment") {
			steps{
				sh 'gcloud container clusters get-credentials kube-cluster --zone us-east4-a'
				sh 'kubectl config view'
				sh "kubectl get deployments"
				sh "kubectl set image deployment/hw3-kitty img-maven=parnavi/img-kitty:${env.BUILD_ID}"
			}
			
		}
	  }
}
