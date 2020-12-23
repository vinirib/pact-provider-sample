#!groovy
pipeline {
    agent {
        docker {
            image 'maven:3.6-jdk-11-openj9'
            args '-v $HOME/.m2:/root/.m2:z --network host'
            reuseNode true
        }
    }
    parameters {
        string(name: 'pactConsumerTags', defaultValue: 'CONTRACT-TEST', description: 'Tags to verify')
    }
    stages {
        stage ('Run Contract Tests'){
            steps {
                sh "mvn clean verify " +
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