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
}