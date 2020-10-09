import jetbrains.buildServer.configs.kotlin.v10.toExtId
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

project(TheProject)

object TheProject: Project ({
    val compile = Maven("Compile", "clean compile")
    val slowTest = Maven("Slow Test", "test", "-Dtest=\"*.unit.*Test\"")
    val fastTest = Maven("Fast Test", "test", "-Dtest=\"*.integration.*Test\"")
    val `package` = Maven("Package", "package", "-DskipTests")

    `package`.triggers {
        vcs {

        }
    }
    `package`.artifactRules =  "**/*.jar"

    val chain = sequential {
        buildType(compile)

        parallel {
            buildType(fastTest)
            buildType(slowTest)
        }
        buildType(`package`)
    }
    chain.buildTypes().forEach { buildType(it)}

    buildTypesOrder = listOf(compile, slowTest, fastTest, `package`)
})

object MyVcsRoot : GitVcsRoot({
    id("HttpsGithubComMarcobehlerDeletemeafterGitRefsHeadsMaster")
    name = DslContext.getParameter("vcsName", "My VCS Repo")
    url = DslContext.getParameter("vcsUrl" )
    branch = "ref/heads/master"
})


class Maven(name: String, goals: String, runnerArgs: String = "") : BuildType({
    id("BuildMe_${name}".toExtId())
    this.name = name

    vcs {
        root(MyVcsRoot)
    }

    steps {
        maven {
            this.goals = goals
            this.runnerArgs = runnerArgs
        }
    }
})