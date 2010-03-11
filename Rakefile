desc "create bundles and upload"
task :deploy do
  libdir = "/Applications/Processing.app/Contents/Resources/Java/libraries/opengl/library"
  name = "winko"
  
  platforms = {
    :mac => %w(libgluegen-rt.jnilib libjogl_awt.jnilib libjogl_cg.jnilib libjogl.jnilib),
    :linux => %w(libgluegen-rt.so libjogl_awt.so libjogl_cg.so libjogl.so),
    :windows => %w(gluegen-rt.dll jogl_awt.dll jogl_cg.dll jogl.dll)
  }
  
  Dir.chdir("target")
  FileUtils.rm_r("build") if File.exist?("build")
  Dir.mkdir("build")
  
  platforms.each_pair do |platform, libs|
    dirname = "build/#{name}-#{platform}"
    FileUtils.rm_r(dirname) if File.exist?(dirname)
    Dir.mkdir(dirname)
    
    
    libs.each do |lib|
      FileUtils.cp "#{libdir}/#{lib}", "#{dirname}/#{lib}"
    end
    
    FileUtils.cp "winko-1.0-SNAPSHOT-jar-with-dependencies.jar", "#{dirname}/"
    Dir.chdir("build")
    system("zip -r #{name}-#{platform}.zip #{name}-#{platform}")
    Dir.chdir("..")
    FileUtils.rm_r(dirname)
  end
  
  # system("scp -r build verknowsys.com:~/public_html/winko/")
  # system("cp -r build ~/public_html/winko/")
  

end

class String
  def /(o)
    File.join(self, o)
  end
end

def show_line(name, stats, color = nil)
  ce = color ? "\033[0m" : ""
  puts  "| #{color}#{name.to_s.capitalize.ljust(20)}#{ce} " + 
        "| #{color}#{stats[:lines].to_s.rjust(7)}#{ce} " +
        "| #{color}#{stats[:loc].to_s.rjust(7)}#{ce} " +
        "| #{color}#{stats[:classes].to_s.rjust(7)}#{ce} " +
        "| #{color}#{stats[:traits].to_s.rjust(7)}#{ce} " +
        "| #{color}#{stats[:methods].to_s.rjust(7)}#{ce} |"
  puts separator
end

def separator
  '+----------------------+---------+---------+---------+---------+---------+'
end

def check_dir(dir)
  Dir.foreach(dir) do |file_name|
    if File.stat(dir / file_name).directory? and (/^\./ !~ file_name)
      check_dir(dir / file_name)
    end

    if file_name =~ /.*\.(scala)$/
      @files[file_name] = @stats[:lines]
      File.open(dir / file_name).each_line do |line|
        @stats[:lines]    += 1
        @stats[:loc]      += 1 unless line =~ /^\s*$/ || line =~ /^\s*#/
        @stats[:classes]  += 1 if line =~ /class [A-Z]/
        @stats[:traits]   += 1 if line =~ /trait [A-Z]/
        @stats[:methods]  += 1 if line =~ /def [a-z]/
      end
      @files[file_name] = @stats[:lines] - @files[file_name]
    end
  end
end

desc "Lines of code statistics"
task :stats do
  STATISTICS_DIRS = {
    :main  => "src/main",
    :test  => "src/test"
  }.reject {|name, dir| !File.exist?(dir) }
  EMPTY_STATS = { :lines => 0, :loc => 0, :classes => 0, :traits => 0, :methods => 0 }
  
  @files = {}
 
  @all = {}
  total = EMPTY_STATS.clone
  ce = "\033[0m"
  cb = "\033[35m"
  cg = "\033[4;32m"
  cr = "\033[31m"
 
  puts separator
  puts "| #{cg}Name#{ce}                 | #{cg}Lines#{ce}   | #{cg}LOC#{ce}     | #{cg}Classes#{ce} | #{cg}Modules#{ce} | #{cg}Methods#{ce} |"
  puts separator
 
  STATISTICS_DIRS.each_pair do |name, dir| 
    @stats = EMPTY_STATS.clone
    check_dir(dir)
    @all[name] = @stats
    show_line(name, @stats)
    @stats.each_pair { |type, count| total[type] += count }
  end
 
  show_line('Total', total, cr)
 
  code_loc = [:main].inject(0) { |sum, e| sum += @all[e][:loc] }
  
  test_loc = 0
  test_loc += @all[:spec][:loc] if @all[:spec]
  test_loc += @all[:test][:loc] if @all[:test]
  test_loc += @all[:features][:loc] if @all[:features]
 
  puts "   Code LOC: #{cb}#{code_loc}#{ce}     Test LOC: #{cb}#{test_loc}#{ce}     Code to test radio:  #{cb}1:%0.2f#{ce}" % (test_loc.to_f / code_loc.to_f)
  puts
  @files.sort {|a,b| b[1] <=> a[1]}.each do |name, lines|
    puts "#{lines.to_s.rjust(5)} - #{name}"
  end
end