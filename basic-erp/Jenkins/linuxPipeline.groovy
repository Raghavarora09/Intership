pipeline {
    agent any

    environment {
        dockerRegistry = "https://index.docker.io/v1/"
        dockerCreds = credentials('dockerhub-credentials')
        backendImage = 'mern-backend'
        frontendImage = 'mern-frontend'
    }

    stages {
        stage('Build Backend') {
            steps {
                script {
                    def backendPath = "basic-erp/backend"
                    if (fileExists(backendPath)) {
                        echo "Building backend image..."
                        sh "docker build -t ${backendImage}:latest ${backendPath}"
                        sh "docker tag ${backendImage} raghavarora09/mern-backend:mern-backend"
                    } else {
                        error "Backend directory ${backendPath} not found"
                    }
                }
            }
        }

        stage('Build Frontend') {
            steps {
                script {
                    def frontendPath = "basic-erp/frontend"
                    if (fileExists(frontendPath)) {
                        echo "Building frontend image..."
                        sh "docker build -t ${frontendImage}:latest ${frontendPath}"
                        sh "docker tag ${frontendImage} raghavarora09/mern-frontend:mern-frontend"
                    } else {
                        error "Frontend directory ${frontendPath} not found"
                    }
                }
            }
        }

        stage('Tag Backend') {
            steps {
                script {
                    echo "Tagging backend image..."
                    sh "docker tag ${backendImage}:latest ${backendImage}:latest"
                }
            }
        }

        stage('Tag Frontend') {
            steps {
                script {
                    echo "Tagging frontend image..."
                    sh "docker tag ${frontendImage}:latest ${frontendImage}:latest"
                }
            }
        }

        stage('Push Backend') {
            steps {
                script {
                    docker.withRegistry("https://index.docker.io/v1/", 'dockerhub-credentials') {
                        echo "Pushing backend image to Docker Hub"
                        sh "docker push raghavarora09/mern-backend:${backendImage}"
                    }
                }
            }
        }

        stage('Push Frontend') {
            steps {
                script {
                    docker.withRegistry(dockerRegistry, 'dockerhub-credentials') {
                        echo "Pushing frontend image to Docker Hub"
                        sh "docker push raghavarora09/mern-frontend:${frontendImage}"
                    }
                }
            }
        }

        stage('Start Services') {
            steps {
                script {
                    echo "Starting services with docker-compose..."
                    sh "docker-compose -f basic-erp/docker-compose.yml up -d"
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
