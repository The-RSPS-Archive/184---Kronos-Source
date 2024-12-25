package io.ruin.api

import java.io.File
import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.concurrent.Executors
import java.util.function.Consumer

/**
 *
 * @project Kronos
 * @author ReverendDread on 3/23/2020
 * https://www.rune-server.ee/members/reverenddread/
 */
class FileWatcherService(var file: File) {

    private val watcher: WatchService = FileSystems.getDefault().newWatchService()
    private val keys: MutableMap<WatchKey, Path> = mutableMapOf()
    private var onCreate: Consumer<WatchEvent<Path>>? = null
    private var onModify: Consumer<WatchEvent<Path>>? = null
    private var onDelete: Consumer<WatchEvent<Path>>? = null
    private var cancelled = false

    init {
        walkAndRegisterDirectories(file.toPath())
    }

    fun start() : FileWatcherService {
        println("File watcher started. Awaiting changes in ${file.name}")
        executorService.submit {
            while (!cancelled) { // wait for key to be signalled
                var key: WatchKey = try {
                    watcher.take()
                } catch (x: InterruptedException) {
                    return@submit
                }
                val dir = keys[key]
                dir?.apply {
                    for (eventRaw in key.pollEvents()) {
                        if (eventRaw.context() !is Path) {
                            continue
                        }
                        val event = eventRaw as WatchEvent<Path>
                        val kind: WatchEvent.Kind<*> = event.kind()
                        // Context for directory entry event is the file name of entry
                        val name = event.context()
                        val child = dir.resolve(name)
                        // if directory is created, and watching recursively, then register it and its sub-directories
                        if (kind === StandardWatchEventKinds.ENTRY_CREATE) {
                            try {
                                if (Files.isDirectory(child)) {
                                    walkAndRegisterDirectories(child)
                                }
                                onCreate?.accept(event)
                            } catch (x: IOException) { // do something useful
                            }
                        } else if (kind === StandardWatchEventKinds.ENTRY_DELETE) {
                            onDelete?.accept(event)
                        } else if (kind === StandardWatchEventKinds.ENTRY_MODIFY) {
                            onModify?.accept(event)
                        }
                    }
                }
                // reset key and remove from set if directory no longer accessible
                val valid = key.reset()
                if (!valid) {
                    keys.remove(key)
                    // all directories are inaccessible
                    if (keys.isEmpty()) {
                        break
                    }
                }
            }
        }
        return this
    }

    fun onFileCreated(consumer: Consumer<WatchEvent<Path>>): FileWatcherService? {
        this.onCreate = consumer
        return this
    }

    fun onFileModified(consumer: Consumer<WatchEvent<Path>>): FileWatcherService? {
        this.onModify = consumer
        return this
    }

    fun onFileDeleted(consumer: Consumer<WatchEvent<Path>>): FileWatcherService? {
        this.onDelete = consumer
        return this
    }

    fun cancel() {
        cancelled = true
        executorService.shutdown()
    }

    @Throws(IOException::class)
    private fun registerDirectory(dir: Path) {
        val key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY)
        keys.put(key, dir)
    }

    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    @Throws(IOException::class)
    private fun walkAndRegisterDirectories(start: Path) { // register directory and sub-directories
        Files.walkFileTree(start, object : SimpleFileVisitor<Path>() {
            @Throws(IOException::class)
            override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
                registerDirectory(dir)
                return FileVisitResult.CONTINUE
            }
        })
    }

    companion object {
        private val executorService = Executors.newCachedThreadPool()
    }

}