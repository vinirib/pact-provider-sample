#!groovy
pipeline {
    agent any
    parameters {
        string(name: 'pactConsumerTags', defaultValue: 'CONTRACT-TEST', description: 'Tags to verify')
    }
    stages {
        stage ('Run Contract Tests'){
            steps {
                sh "chmod +x mvnw"
                sh "./mvnw clean verify " +
                        "-Dpact.provider.version=${GIT_COMMIT} " +
                        "-Dpact.verifier.publishResults=true " +
                        "-Dpact.provider.tag=${params.pactConsumerTags}"
            }
        }
    }
    post {
        success {
            script {
                echo "Contract Generated Successful, now trigger Provider check!"
                build job: '3-PACT-FLOW-CONSUMER-CAN-I-DEPLOY', propagate: true
            }
        }
    }
}