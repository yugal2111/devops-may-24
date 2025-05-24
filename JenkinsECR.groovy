pipeline {
    agent any

    stages {
        stage('Login to ECR') {
            steps {
                sh "aws ecr get-login-password --region us-east1 > login.txt"
                sh "docker login -u AWS -p $(cat login.txt) ${ecrRepositoryUrl}"
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${dockerImageName} ."
            }
        }
        stage('Tag Docker Image') {
            steps {
                sh "docker tag ${dockerImageName} ${ecrRepositoryUrl}/${dockerImageName}"
            }
        }
        stage('Push Docker Image') {
            steps {
                sh "docker push ${ecrRepositoryUrl}/${dockerImageName}"
            }
        }
    }
}