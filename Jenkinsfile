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
                        "-Dpactbroker.tags=${params.pactConsumerTags}"
            }
        }
    }
    post {
        success {
            script {
                echo "Contract Generated Successful, now trigger Provider check!"
                build job: '3-PACT-FLOW-CONSUMER-CAN-I-DEPLOY', propagate: true

                echo "Contract Generated Successful, now trigger Consumer check!"
                build job: '4-PACT-FLOW-PROVIDER-CAN-I-DEPLOY', propagate: true
            }
        }
    }
}