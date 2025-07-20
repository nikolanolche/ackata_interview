pipeline {
    agent any

    environment {
        IMAGE_NAME = 'api-tests'
        REPORTS_DIR = 'reports'
    }

    stages {

        stage('Clean workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t ${IMAGE_NAME} .'
                }
            }
        }

        stage('Run Tests in Docker') {
            steps {
                script {
                    // Create reports directory if it doesn't exist
                    sh 'mkdir -p ${REPORTS_DIR}'

                    // Run tests inside container and copy out surefire reports
                    sh """
                        docker run --rm \
                            -v "\$PWD/${REPORTS_DIR}:/app/target/surefire-reports" \
                            ${IMAGE_NAME}
                    """
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                junit "${REPORTS_DIR}/*.xml"
            }
        }
    }

    post {
        always {
            echo "Cleaning up..."
            sh 'docker rmi ${IMAGE_NAME} || true'
        }
    }
}
