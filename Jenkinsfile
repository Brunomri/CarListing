Jenkinsfile (Declarative Pipeline)

pipeline {
    agent { docker { image 'maven:3.3.3' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --clean'
                sh 'mvn --package'
            }
        }
        stage('test') {
            steps {
                sh 'mvn --test'
            }
        }
    }
}
