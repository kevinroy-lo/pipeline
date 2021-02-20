pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
              sh 'gradle clean dokkaHtml'
            }
            post {
                success {
                   javadoc javadocDir: 'build/docs/dokkahtml', keepAll: true
                }
            }
        }
    }
}