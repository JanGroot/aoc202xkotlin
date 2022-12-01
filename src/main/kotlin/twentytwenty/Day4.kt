fun main() {
    val records = {}.javaClass.getResource("twentytwenty/input4.txt").readText().split("\n\n")
    val allPassports = records.map { record ->
        record.split("\\s".toRegex()).map {
            val (key, value) = it.split(":")
            key to value
        }.toMap()
    }
    println(allPassports.count { it.hasRequiredFields() })
    println(allPassports.count { it.isValid() })
}

fun Map<String, String>.hasRequiredFields() = keys.containsAll(validations.keys)
fun Map<String, String>.isValid() = validations.all { this[it.key]?.contains(it.value.toRegex()) ?: false }

private val validations = mapOf(
    "eyr" to "(202\\d|2030)",
    "hgt" to "((1[5-8]\\d|19[0-3])cm|^(59|6\\d|7[0-6])in)",
    "ecl" to "(amb|blu|brn|grn|gry|hzl|oth)",
    "byr" to "(19[2-9]\\d|200[0-2])",
    "iyr" to "(201\\d|2020)",
    "hcl" to "^#[0-9a-fA-F]{6}$",
    "pid" to "^\\d{9}$"
)
