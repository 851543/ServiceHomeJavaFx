package com.alibaba.mybatis.entity

import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


packageName = "com.token.entity"
typeMapping = [
        (~/(?i)int8/)                     : "Long",
        (~/(?i)int/)                      : "Integer",
        (~/(?i)bool/)                     : "Boolean",
        (~/(?i)float|double|decimal|real/): "Double",
        (~/(?i)jsonb/)                    : "JSONObject",
        (~/(?i)datetime|timestamp/)       : "LocalDateTime",
        (~/(?i)date/)                     : "java.util.Date",
        (~/(?i)time/)                     : "java.util.Time",
        (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    packageName = getPackageName(dir)   //自动生成包的位置
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(dir, className + ".java")), "UTF-8"))
    printWriter.withPrintWriter { out -> generate(out, className, fields, table) }
//输出实体类
}

// 获取包所在文件夹路径
def getPackageName(dir) {
    return dir.toString().replaceAll("\\\\", ".").replaceAll("/", ".").replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
}

def generate(out, className, fields, table) {
    out.println "package $packageName"
    out.println ""
    out.println "import com.baomidou.mybatisplus.annotation.TableId;"
    out.println "import com.baomidou.mybatisplus.annotation.TableField;"
    out.println "import com.baomidou.mybatisplus.annotation.TableName;"
    out.println "import java.io.Serializable;"
    out.println "import java.time.LocalDateTime;"
    out.println ""
    // 生成开头文档
    out.println ""
    out.println "/** \n" +
            " * @author 阿俊\n" + //1. 修改idea为自己名字
            " * @description  \n" +
            " */"
    out.println "@TableName(value =\"" + table.getName() + "\")"
    out.println "public class $className  implements Serializable {"
    out.println ""
    out.println "    private static final long serialVersionUID = 1L;"
    fields.each() {
        out.println ""
        if (it.name == "id") out.println "    @TableId(value=\"${it.field}\")"
        else if (it.type == "JSONObject") out.println "    //@TableName(value =\"" + table.getName() + "\", autoResultMap = true)\n    @TableField(value=\"${it.field}\", typeHandler = JacksonTypeHandler.class)"
        else out.println "    /**\n" +
                    "     * ${it.commoent} \n" +
                    "     */"
        out.println "    @TableField(value=\"${it.field}\")"
        if (it.name == "id") out.println "    private Long ${it.name};"
        else out.println "    private ${it.type} ${it.name};"
    }
    out.println ""

    out.println "}"
}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        def comm = [
                field   : col.getName(),
                name    : javaName(col.getName(), false),
                type    : typeStr,
                commoent: col.getComment()
        ]
        //对于表中主键自定义注解
        if ("pk".equals(Case.LOWER.apply(col.getName()))) {
            comm.annos = "\t@Id\n"
            //自增主键需要
            comm.annos += "@Column(name = \"" + col.getName() + "\")"
        }
        fields += [comm]//字段对照
    }
}

def javaName(str, capitalize) {
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}


def isNotEmpty(content) {
    return content != null && content.toString().trim().length() > 0
}
