pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts(artifacts: 'target/blep', onlyIfSuccessful: true, fingerprint: true)
            }
        }
        stage('Notify') {
            steps {
                slackSend color: 'good', message: 'Building PoneCrafter branch ${branch} complete.'
            }
        }
    }
}