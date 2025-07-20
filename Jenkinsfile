pipeline {
    agent any

    environment {
        IMAGE_TAG = "api-tests:${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_TAG} ."
            }
        }

        stage('Run Tests in Docker') {
            steps {
                sh "docker run --rm -v \$WORKSPACE:/app ${IMAGE_TAG} mvn test"
            }
        }

        stage('Check Reports') {
            steps {
                sh 'ls -la target/surefire-reports || echo "No report files found"'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**/*.*', fingerprint: true

            // Cleanup dangling images to avoid disk bloat
            sh 'docker image prune -f || true'

            publishHTML(target: [
               reportName: 'TestNG HTML Report',
               reportDir: 'target/surefire-reports',
               reportFiles: 'index.html',
               keepAll: true,
               alwaysLinkToLastBuild: true,
               allowMissing: false
            ])
        }
    }
}
