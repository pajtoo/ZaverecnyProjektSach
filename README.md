Tento projekt slouží aktuálně jako cvičný, na kterém si v současnosti zkouším vytvořit samostatné univerzální, flexibilní služby (validace, mapping). Tyto služby proto pracují s reflexí. Pokračovat ve vývoji bude možné především 22-30.10.

Proto, aby byly určité hodnoty (zprávy uživateli, validační kritéria) více autonomní a centralizované, a tedy jednodušeji spravovatelné, udržuji je ve výčtových typech. 

----------------------------

Aplikaci jsem restrukturalizoval tak, aby se o její řízení staral Controller, který dává View pokyny, k jakým položkám chce dodat hodnotu a posléze z něj přijímá uživatelský/é vstup/y a odesílá Validátoru. 
 - V případě, že vstup není validní, vyhazuje Validátor výjimku s chybovou zprávou, kterou posléze Controller předává do view. Controller následně UzivatelskeRozhrani žádá o nový vstup z konzole, a to do té doby, než dostane správný. 

Předpřipravil jsem práci s cloud PostgreSQL databází, ale nyní zbývá ještě dodělat práci na view a controller vrstvě, na které jsem se posléze zaměřil především. 

PS.
1)
z důvodu, že aplikace byla psaná česky, jsem se snažil tento ráz zachovávat, ale někdy mi to nedalo, když to znělo dost divně (př. InsuredController X PojistenecController). 

2) v druhé aplikaci jsem používal IOC a v této si chci pro praxi vyzkoušet ruční předávání závislostí, a až bude aplikace hotová, udělat na jiné větvi verzi s IOC

3) je obrovské množství způsobů, jak se dá každá věc implementovat, neměl jsem příliš čas zkoumat všechna existující řešení, sáhnul jsem tedy po těch jejichž řešení mi v hlavě získalo poměrně jasné obrysy a přišlo mi celkem fajn (Validace + enumy (ty by se daly nejspíš předělat do ještě lepší podoby)).