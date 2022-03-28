package me.qboi.generators.words

import org.eclipse.swt.SWT
import org.eclipse.swt.dnd.Clipboard
import org.eclipse.swt.dnd.TextTransfer
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.graphics.ImageLoader
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import org.eclipse.swt.widgets.List
import java.util.*
import kotlin.collections.ArrayList

class Main() {
    companion object {
        lateinit var instance: Main

        @JvmStatic
        fun main(vararg args: String) {
            val main = Main()
            main.postInit()
        }
    }

    private val versionMenuItems: MutableMap<Version, MenuItem> = mutableMapOf()
    private lateinit var seedTxt: Text

    // Components
    private var display: Display
    private var shell: Shell
    private var menuBar: Menu
    private var list: List

    private var version: Version = Version.GEN_2
        set(value) {
            field = value
            shell.text = "Word Generator - " + value.versionName
        }

    init {
        preInit()
        display = Display()

        initImages()
        initIcons()

        shell = Shell(display)

        initShell(shell)

        val layout = GridLayout(2, false)
        shell.layout = layout
        layout.marginTop = 0
        layout.marginLeft = 0
        layout.marginRight = 0
        layout.marginBottom = 0

        shell.setSize(300, 800)
        shell.minimumSize = Point(275, 300)
        shell.maximumSize = Point(350, 800)

        list = List(shell, SWT.SINGLE or SWT.V_SCROLL)

        val listData = GridData()
        listData.horizontalAlignment = GridData.FILL
        listData.verticalAlignment = GridData.FILL
        listData.grabExcessHorizontalSpace = true
        listData.grabExcessVerticalSpace = true
        listData.horizontalSpan = 1
        list.layoutData = listData

        val panel = Composite(shell, SWT.SINGLE)

        val panelData = GridData()
//        listData.horizontalAlignment = GridData.FILL;
        panelData.verticalAlignment = GridData.FILL
//        panelData.grabExcessHorizontalSpace = true;
        panelData.grabExcessVerticalSpace = true
        panelData.widthHint = 120
        listData.horizontalSpan = 1
        panel.layoutData = panelData

        panel.layout = GridLayout()

        fun newButtonLayout(): GridData {
            return GridData(GridData.FILL, GridData.CENTER, true, false)
        }

        val randomizeBtn = Button(panel, SWT.PUSH).also {
            it.text = "Randomize"
            it.addSelectionListener(SelectionCommand {
                val nextLong = Random(System.currentTimeMillis()).nextLong(Long.MIN_VALUE, Long.MAX_VALUE)
                seedTxt.text = nextLong.toString()

                generateList(nextLong.toString())
            })
            it.layoutData = newButtonLayout()
        }

        val copyBtn = Button(panel, SWT.PUSH).also {
            it.text = "Copy"
            it.addSelectionListener(SelectionCommand {
                if (list.selection.isNotEmpty()) {
                    val s = list.selection[0]
                    val clipboard = Clipboard(display)
                    clipboard.setContents(arrayOf(s), arrayOf(TextTransfer.getInstance()))
                }
            })
            it.layoutData = newButtonLayout()
        }

        val sentenceBtn = Button(panel, SWT.PUSH).also {
            it.text = "Sentence"
            it.addSelectionListener(SelectionCommand {
                var lowercase = generate(seedTxt.text!!, 4..7).joinToString(" ").lowercase()
                lowercase = if (lowercase.isNotEmpty()) lowercase[0].uppercase() + lowercase.substring(1) else lowercase
                SentenceDialog(shell, "$lowercase.").open()
            })
            it.layoutData = newButtonLayout()
        }

        seedTxt = Text(shell, SWT.SINGLE)
        seedTxt.text = ""
        seedTxt.message = "Seed"
        seedTxt.layoutData = newButtonLayout()

        val seedData = GridData()
        seedData.horizontalAlignment = GridData.FILL
        seedData.horizontalSpan = 1
        seedTxt.layoutData = seedData

        val generateBtn = Button(shell, SWT.PUSH)
        generateBtn.text = "Generate"
        generateBtn.addSelectionListener(SelectionCommand {
            generateList(seedTxt.text)
        })

        val generateData = GridData()
        generateData.horizontalAlignment = GridData.FILL
        generateData.widthHint = 120
        generateData.horizontalSpan = 1
        generateBtn.layoutData = generateData


        menuBar = Menu(shell, SWT.BAR)
        initMenuBar(menuBar)
        shell.menuBar = menuBar
    }

    private fun generateList(seed: String) {
        list.setItems(*generate(seed).toTypedArray())
    }

    private fun generate(seed: String): ArrayList<String> {
        println("Seed : $seed")
        val generator = version.build(seed.toLongOrNull().let { number ->
            when (number) {
                null -> return@let hash(seed.lowercase(Locale.ROOT))
                else -> return@let number
            }
        }.also { hash ->
            println("Seed Number: $hash")
        })
        list.setItems()

        val words: ArrayList<String> = arrayListOf()
        for (i in 0..50) {
            words += generator.generate()
        }
        return words
    }

    private fun generate(seed: String, range: IntRange): ArrayList<String> {
        println("Seed : $seed")
        val s: Long
        val generator = version.build(seed.toLongOrNull().let { number ->
            when (number) {
                null -> return@let hash(seed.lowercase(Locale.ROOT))
                else -> return@let number
            }
        }.also { hash ->
            s = hash
            println("Seed Number: $hash")
        })
        list.setItems()

        val words: ArrayList<String> = arrayListOf()
        for (i in 0..Random(s).nextInt(range.first, range.last - 1)) {
            words += generator.generate()
        }
        return words
    }

    // adapted from String.hashCode()
    private fun hash(string: String): Long {
        var h = 0L
        val len = string.length
        for (i in 0 until len) {
            h = 31 * h + string[i].code
        }
        return h
    }

    private fun preInit() {
        instance = this
    }

    private fun initShell(shell: Shell) {
        version = Version.GEN_2
    }

    private fun initImages() {

    }

    private fun initIcons() {

    }

    private fun initMenuBar(menu: Menu) {
        val versionsItem = MenuItem(menu, SWT.MENU)
        versionsItem.text = "Versions"
        val versions = Menu(versionsItem)
        versionsItem.menu = versions

        for (version in Version.values()) {
            val item = MenuItem(versions, SWT.RADIO)
            item.text = version.versionName
            item.addSelectionListener(SelectionCommand {
                this.version = version
            })
            versionMenuItems[version] = item
        }

        versionMenuItems[Version.GEN_2]!!.selection = true
    }

    private fun postInit() {
        shell.pack()
        shell.size = Point(600, 450)
        shell.open()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) {
                display.sleep()
            }
        }
        display.dispose()
    }

    fun loadMultiResIcon(path: String): Array<Image> {
        return Arrays.stream(ImageLoader().load(this::class.java.getResourceAsStream("/$path"))).map {
            Image(display, it)
        }.toList().toTypedArray()
    }

    fun loadImage(path: String): Image {
        var path1 = path
        if (!path1.endsWith(".png")) {
            path1 += ".png"
        }
        return Image(display, ImageLoader().load(this::class.java.getResourceAsStream("/$path1"))[0])
    }
}