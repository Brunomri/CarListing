pipeline {
    agent { label 'dockerserver' } // if you don't have other steps, 'any' agent works
    stages {
        stage('Back-end') {
            agent {
                docker {
                  label 'dockerserver'  // both label and image
                  image 'maven:3.8.1-adoptopenjdk-11'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
