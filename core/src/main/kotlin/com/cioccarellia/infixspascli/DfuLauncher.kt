import com.cioccarellia.infixspascli.web.SpasRequestComposer
import com.cioccarellia.infixspascli.web.SpasScraper

private val default_spas_program = """
    begin_problem(Pelletier54).

    list_of_descriptions.
    name({*Pelletier's Problem No. 54*}).
    author({*Christoph Weidenbach*}).
    status(unsatisfiable).
    description({*Problem taken in revised form from the "Pelletier Collection", Journal of Automated
    	Reasoning, Vol. 2, No. 2, pages 191-216*}).
    end_of_list.

    list_of_symbols.
    end_of_list.

    list_of_formulae(axioms).
    end_of_list.

    list_of_formulae(conjectures).

    end_of_list.

    list_of_settings(SPASS).
    {*
    set_flag(PGiven,1).
    set_flag(PProblem,0).
    *}
    end_of_list.

    end_problem.
""".trimIndent().trim()

fun main(args: Array<String>) {
    println(SpasScraper.extractText(SpasRequestComposer.execute(default_spas_program)))
}