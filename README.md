Tato aplikace slouží k evidenci pojištěných. Hlavní nabídka umožňuje vytvoření nového pojištěného, výpis všech pojištěných a vyhledávání pojištěných. Je možnost zvolit si vyhledávání buď podle id, anebo podle hodnoty libovolných parametrů daného pojištěnce. 

    Po dokončení vyhledávání nebo výpisu všech pojištěných aplikace vypíše nabídku, která umožňuje s těmito záznamy dále pracovat - upravovat je anebo mazat. V případě nalezení většího množství pojištěných si aplikace ještě pro upřesnění vyžádá id pojištěného. 

    Úprava a vyhledávání pojištěného je řešena tím způsobem, že se aplikace postupně ptá na všechny atributy pojištěného (s výjimkou id) a vždy je možnost ponechat pole prázdné. U atributů, jejichž hodnota nebyla vyplněna, aplikace v případě editace ponechává původní hodnotu, v případě vyhledávání nejsou tyto atributy zařazeny mezi kritéria vyhledávání. 

    O validaci uživatelského vstupu se stará validâtor, který se řídí validačními kritérii specifikovanými jednotlivě pro každou položku ve výčtovém typu, který je jeho součástí. Pro jednoduché položky, u nichž se kontroluje délka textu nebo velikost hodnoty čísla, lze tedy jednoduše do výčtového typu přidat nový záznam s jejich minimální a maximální hodnotou. 

    V případě chybného uživatelského vstupu se zobrazí uživatelsky přívětivâ zpráva s informací o nedostatku, který validace odhalila. Následně je uživatel vyzván k zadání nové hodnoty. 

    Vedle validace se Validátor stará také o standardizaci vstup. Odstraňuje případné mezery na začátcích a koncích vstupu. U jmen nastavuje první písmeno jako velké, odstraňuje případné dvojité mezery uvnitř vstupu. U telefonu odstraňuje případné mezery mezi jednotlivými číslicemi a v případě nezadání mezinárodní předvolby doplňuje jako výchozí předvolbu pro Česko. Některé z těchto operací lze snadno deaktivovat vyjmutím příslušného požadavku z výše zmíněného validačního výčtového typu. 

    Záznamy se ukládají do cloudové databáze, a tudíž jsou zabezpečeny proti ztrátě i odcizení. 
    
Aplikace je navržena tak, aby bylo možné bez větších komplikací pridávat pojištěnému další atributy. 
Sv případě potřeby lze také snadno přidat do aplikace další jazykové mutace. 
