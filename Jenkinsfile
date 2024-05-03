pipeline {
    agent any
    tools {
        maven 'MAVEN_3_9_6'
        jdk 'JDK_17'
    }
    stages {
        stage ('Compile Stage') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile'
                    } else {
                        bat 'mvn clean compile'
                    }
                }
            }
        }
        stage ('Testing Stage') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
        }
        stage ('Package Stage') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn package'
                    } else {
                        bat 'mvn package'
                    }
                }
            }
        }
    }
}