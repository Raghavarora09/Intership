pipeline {
    agent any

    environment {
        dockerRegistry = "https://index.docker.io/v1/"
        dockerCreds = credentials('dockerhub-credentials')
        backendImage = 'raghavarora09/mern-backend'
        frontendImage = 'raghavarora09/mern-frontend'
    }

    stages {
        stage('Build Backend') {
            steps {
                script {
                    def backendPath = "basic-erp/backend"
                    if (fileExists(backendPath)) {
                        bat "docker build -t ${backendImage}:latest ${backendPath}"
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
                        bat "docker build -t ${frontendImage}:latest ${frontendPath}"
                    } else {
                        error "Frontend directory ${frontendPath} not found"
                    }
                }
            }
        }

        stage('Push Backend') {
            steps {
                script {
                    docker.withRegistry(dockerRegistry, dockerCreds) {
                        echo "Pushing backend image to Docker Hub"
                        bat "docker push ${backendImage}:latest"
                    }
                }
            }
        }

        stage('Push Frontend') {
            steps {
                script {
                    docker.withRegistry(dockerRegistry, dockerCreds) {
                        echo "Pushing frontend image to Docker Hub"
                        bat "docker push ${frontendImage}:latest"
                    }
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
