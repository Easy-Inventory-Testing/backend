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
                    sh 'mvn clean compile'
                }
            }
        }
        stage ('Testing Stage') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
        stage ('Package Stage') {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }
    }
}