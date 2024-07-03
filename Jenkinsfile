
pipeline {
    agent any
    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/VivekKumar432/LPU-Intership-2.git'
            }
        }
        stage('Build Backend') {
            steps {
                sh 'docker-compose build backend'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'docker-compose run backend npm test'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('erp-system-backend:latest')
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        docker.image('erp-system-backend:latest').push()
                    }
                }
            }
        }
    }
}