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

    stage('Checkout') {
      steps{
        echo "------------>Checkout<------------"
        checkout scm
      }
    }
  
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
      sh './gradlew test'
    }
  }

  stage('Static Code Analysis') {
    steps{
      echo '------------>Análisis de código estático<------------'
      withSonarQubeEnv('Sonar') {
        sh "${tool name: 'SonarScanner-Mac', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"

        echo "SONAR_HOST_URL:$SONAR_HOST_URL"
        echo "SONAR_AUTH_TOKEN:$SONAR_AUTH_TOKEN"
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
      junit 'app/build/test-results/test/*.xml' //RUTA DE TUS ARCHIVOS .XML, SI LOS ARCHIVOS NO EXISTEN ESTA INSTRUCCIÓN FALLARÁ
        mail (to: 'steve.campos@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
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
