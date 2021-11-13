pipeline{
	agent any
		tools{
			jdk 'JAVA_11'
			mave 'Maven 3.3.9'
}
		stages{
			stage('build'){
			   steps{
				sh "mvn clean"
				}
			     }
			stage('COdeAnalysis'){
					 steps{ junit allowEmptyResults:true }  
					  }
			}
}