#!groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "chmod +x mvnw"
                sh "./mvnw clean verify"
            }
        }
    }
    post {
        success {
            script {
                echo "Contract Generated Successful, now trigger Provider check!"
                build job: 'PACT-FLOW-PROVIDER-CAN-I-DEPLOY', propagate: true

                echo "Contract Generated Successful, now trigger Consumer check!"
                build job: 'PACT-FLOW-CONSUMER-CAN-I-DEPLOY', propagate: true
            }
        }
    }
}