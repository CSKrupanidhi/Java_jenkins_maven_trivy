pipeline {
    agent any

    tools {
        // Ensure this matches your Maven name under Jenkins -> Manage Jenkins -> Tools
        maven 'Maven-3.9'
    }

    environment {
        IMAGE_NAME = "demo-java-app"
        IMAGE_TAG  = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven: Clean, Test & Package') {
            steps {
                echo 'Building Java app with Maven...'
                sh 'mvn clean package'
            }
        }

        stage('Trivy: Scan Dependencies (SCA)') {
            steps {
                echo 'Checking dependencies in pom.xml for CVEs...'
                // Scans the workspace directory, reports high & critical flaws
                sh 'trivy fs --severity HIGH,CRITICAL --format table .'
            }
        }

        stage('Docker: Build Container Image') {
            steps {
                echo 'Packaging JAR into container...'
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Trivy: Scan Container Image') {
            steps {
                echo 'Auditing Docker image layers...'
                // 1. Prints findings table to Jenkins console
                sh "trivy image --severity LOW,MEDIUM,HIGH,CRITICAL ${IMAGE_NAME}:${IMAGE_TAG}"

                // 2. Fails the build if any CRITICAL vulnerabilities are found
                sh "trivy image --exit-code 1 --severity CRITICAL ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    }

    post {
        always {
            sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true"
            cleanWs()
        }
    }
}