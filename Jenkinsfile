pipeline {
    agent any
    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Starting Checkout Stage...'
                git branch: 'main', url: 'https://github.com/Raghavarora09/Intership.git'
                echo 'Completed Checkout Stage'
            }
        }
        stage('Build Backend') {
            steps {
                echo 'Starting Build Backend Stage...'
                script {
                    bat 'dir ./basic-erp/backend' // Debug step to list contents of the directory
                    bat 'cd ./basic-erp/backend && docker-compose build backend'
                }
                echo 'Completed Build Backend Stage'
            }
        }
        stage('Run Tests') {
            steps {
                echo 'Starting Run Tests Stage...'
                script {
                    bat 'cd ./basic-erp/backend && docker-compose run backend npm test'
                }
                echo 'Completed Run Tests Stage'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Starting Build Docker Image Stage...'
                script {
                    bat 'cd ./basic-erp/backend && docker build -t erp-system-backend:latest .'
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
