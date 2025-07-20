pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t api-tests .'
            }
        }

        stage('Run Tests in Docker') {
            steps {
                sh 'docker run --rm -v $WORKSPACE:/app api-tests mvn test'
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
            archiveArtifacts artifacts: 'target/surefire-reports/**/*.html', fingerprint: true

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
