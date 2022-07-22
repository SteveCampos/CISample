pipeline {
  agent {
    label 'Slave_Mac'
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
 	disableConcurrentBuilds()
  }

  tools {
    jdk 'JDK11_Mac'
  }


  stages{
  
  stage('Compile') {
    steps {
      echo "------------>Compile<------------"
      sh 'chmod +x ./gradlew'
      sh './gradlew build -x test'
    }
  }

  stage('Unit Tests') {
    steps{
      echo "------------>Unit Tests<------------"
      sh './gradlew clean'
      sh 'env ANDROID_SERIAL=emulator-5556 ./gradlew jacocoTestReport'
    }
  }

  stage('Static Code Analysis') {
    steps{
      echo '------------>Análisis de código estático<------------'
      withSonarQubeEnv('Sonar') {
        sh "${tool name: 'SonarScanner-Mac', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
      }
    }
  }
}


  post {
    always {
      echo "currentPipelineBuild: ${currentBuild.fullDisplayName}"
      echo 'This will always run'
    }
    success {
      echo 'This will run only if successful'
      junit testResults: 'presentation/build/test-results/**/TEST-*.xml'
      mail (to: 'steve.campos@ceiba.com.co',subject: "Success Pipeline:${currentBuild.fullDisplayName}",body: "Success pipeline ${env.BUILD_URL}")
    }
    failure {
      echo 'This will run only if failed'
      mail (to: 'steve.campos@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
    }
    unstable {
      echo 'This will run only if the run was marked as unstable'
    }
    changed {
      echo 'This will run only if the state of the Pipeline has changed'
      echo 'For example, if the Pipeline was previously failing but is now successful'
    }
  }
}
