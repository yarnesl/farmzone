using System;
using System.IO;

namespace MyNamespace {
    class MyClass {
        static void Main() {

            Console.WriteLine("Program started");

            FileSystemWatcher watcher = new FileSystemWatcher();
            string path = "C:\\Users\\tenne\\eclipse-workspace\\farmzone\\target";

            watcher.Path = path;
            watcher.Changed += OnChanged;
            watcher.Filter = "*.jar";

            watcher.EnableRaisingEvents = true;
            Console.ReadLine();

        }

        private static void OnChanged(object sender, FileSystemEventArgs e) {
            Console.WriteLine("File Changed: {0}", e.Name);
            File.Copy(e.FullPath, "C:\\Users\\tenne\\Desktop\\craftbukkit server\\plugins\\"+e.Name, true);
            Console.WriteLine("File Copied to Destination Successfully");
        }
    }
}