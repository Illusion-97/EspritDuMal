def remote = [:]
remote.name = 'docker'
remote.host = '185-227-110-33.cloud-xip.com'
remote.user = 'root'
remote.password = 'D3struction971'
remote.allowAnyHosts = true

pipeline {
    agent any
    tools {
        maven 'Maven 3.9.2'
    }
    stages {
        stage('maven build') {
            steps {
                bat 'mvn clean package -Dmaven.test.skip'
            }
        }
        stage('build_image') {
            steps {
                script {
                    sshPut remote: remote, from: 'target/EspritDuMal.jar', into: './edm'
                    sshPut remote: remote, from: 'Dockerfile', into: './edm'
                    sshCommand remote: remote, command: "docker build -f ~/edm/Dockerfile -t edm:latest ~/edm"
                    echo 'image build OK '
                }
            }
        }
        stage('execution') {
            steps {
                script {
                    try {
                        sshCommand remote: remote, command: "docker stop EspritDuMal"
                        sshCommand remote: remote, command: "docker rm EspritDuMal"
                    } catch(Exception e) {
                        echo "non trouvé"
                    }
                    sshCommand remote: remote, command: "echo \$DISCORD_TOKEN"
                    sshCommand remote: remote, command: "docker run --name EspritDuMal -d -e DISCORD_TOKEN=\$EDM_TOKEN -e SPRING_DATASOURCE_URL=\$EDM_SPRING_DATASOURCE_URL -e SPRING_DATASOURCE_USERNAME=\$EDM_SPRING_DATASOURCE_USERNAME -e DISCORD_SPRING_DATASOURCE_PASSWORD=\$EDM_SPRING_DATASOURCE_PASSWORD -p 911:911 edm:latest EspritDuMal.jar"
                }
            }
        }
    }
}
