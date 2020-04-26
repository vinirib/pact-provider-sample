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
        stage('Check Pact Verifications') {
            steps {
                sh 'curl -LO https://github.com/pact-foundation/pact-ruby-standalone/releases/download/v1.82.3/pact-1.82.3-linux-x86_64.tar.gz'
                sh 'tar xzf pact-1.82.3-linux-x86_64.tar.gz'
                dir('pact/bin') {
                    sh "./pact-broker can-i-deploy -a AccountBalanceProvider -b http://pact_broker -e ${GIT_COMMIT}"
                }
            }
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'Deploying to prod now...'
            }
        }
    }

}