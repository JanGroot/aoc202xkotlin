fun main() {
    val records = {}.javaClass.getResource("input4.txt").readText().split("\n\n")
    val allPassports = records.map { record ->
        record.split(" ", "\n").map { field ->
            val (key, value) = field.split(":")
            key to value
        }.toMap()
    }.map { PassPort(it) }
    println(allPassports.count { it.hasRequiredFields() })
    println(allPassports.count { it.isValid() })
}

data class PassPort(val fields: Map<String, String>) {
    fun hasRequiredFields() = fields.keys.containsAll(listOf("eyr", "hgt", "ecl", "byr", "iyr", "hcl", "pid"))
    fun isValid() =
        hasRequiredFields() && validBirthYear() && validIssueYear() && validExpireYear() && validHeight() && validHairColor() && validEyeColor() && validPassPortId()

    private fun validBirthYear() = fields["byr"]!!.toInt() in 1920..2002
    private fun validIssueYear() = fields["iyr"]!!.toInt() in 2010..2020
    private fun validExpireYear() = fields["eyr"]!!.toInt() in 2020..2030
    private fun validHeight(): Boolean {
        val hgt = fields["hgt"]!!
        return when {
            (hgt.endsWith("cm")) -> hgt.substringBefore("cm").toInt() in 150..193
            (hgt.endsWith("in")) -> hgt.substringBefore("in").toInt() in 59..76
            else -> false
        }
    }

    private fun validHairColor() = fields["hcl"]!!.matches("""^#[0-9a-fA-F]{6}$""".toRegex())
    private fun validEyeColor() = fields["ecl"] in listOf("amb", "blu", "brn", "grn", "gry", "hzl", "oth")
    private fun validPassPortId() = fields["pid"]!!.matches("""\d{9}""".toRegex())
}