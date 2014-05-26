module.exports = (grunt) ->
  grunt.initConfig
    watch:
      options:
        livereload: true
      js:
        files: ['js/**/*.js']
      styles: 
        files: ['css/*.css']
      views: 
        files: ['view/*.html']
    nodemon:
      dev:
        script: 'server.js'
    concurrent:
      all:
        options:
          logConcurrentOutput: true
        tasks: ['watch', 'nodemon']

  grunt.loadNpmTasks 'grunt-contrib-watch'
  grunt.loadNpmTasks 'grunt-concurrent'
  grunt.loadNpmTasks 'grunt-nodemon'

  grunt.registerTask 'default', 'concurrent'
