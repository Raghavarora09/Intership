pipeline {
    agent any
    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Starting Checkout Stage...'
                git 'https://github.com/Raghavarora09/Intership.git'
                echo 'Completed Checkout Stage'
            }
        }
        stage('Build Backend') {
            steps {
                echo 'Starting Build Backend Stage...'
                dir('basic-erp/backend') {
                    sh 'docker-compose -f ../docker-compose.yml build backend'
                }
                echo 'Completed Build Backend Stage'
            }
        }
        stage('Run Tests') {
            steps {
                echo 'Starting Run Tests Stage...'
                dir('basic-erp/backend') {
                    sh 'docker-compose -f ../docker-compose.yml run backend npm test'
                }
                echo 'Completed Run Tests Stage'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Starting Build Docker Image Stage...'
                dir('basic-erp/backend') {
                    script {
                        docker.build('erp-system-backend:latest')
                    }
                }
                echo 'Completed Build Docker Image Stage'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Starting Deploy Stage...'
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        docker.image('erp-system-backend:latest').push()
                    }
                }
                echo 'Completed Deploy Stage'
            }
        }
    }
}
