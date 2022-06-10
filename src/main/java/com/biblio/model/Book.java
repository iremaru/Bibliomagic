package com.biblio.model;

public class Book {

    private int code;
    private String cover;
    private String title;
    private String author;
    private String editorial;
    private Subject subject;
    private BookState state;
    private Boolean isAvailable;

    public enum BookState
    {
        New("Nuevo"),
        NewLike("Cuasinuevo"),
        Used("Usado"),
        Rickety("Desvencijado");

        private String inSpanish;

        BookState(String inSpanish)
        {
            this.inSpanish = inSpanish;
        }
        public String ToSpanish()
        {
            return inSpanish;
        }
    }

    public enum Subject
    {
        Astronomy(Type.Core, "Astronomía"),
        Charms(Type.Core, "Encantamientos"),
        DefenceAgainstTheDarkArts(Type.Core, "DefensaContraLasArtesOscuras"),
        Herbology(Type.Core, "Herbología"),
        HistoryOfMagic(Type.Core, "HistoriaDeLaMagia"),
        Potions(Type.Core, "Pociones"),
        Transfiguration(Type.Core, "Transformaciones"),
        Arithmancy(Type.Elective, "Aritmancia"),
        CareOfMagicalCreatures(Type.Elective, "Cuidado de Criaturas Mágicas"),
        Divination(Type.Elective, "Adivinación"),
        MuggleStudies(Type.Elective, "Estudios Muggles"),
        StudyOfAncientRunes(Type.Elective, "Estudio de Runas Antiguas"),
        AdvancedArithmancyStudies(Type.Elective, "Estudios Avanzados de Aritmancia"),
        Alchemy(Type.Elective, "Alquimia"),
        Apparition(Type.Elective, "Aparición"),
        AncientStudies(Type.Extracurricular, "Estudios Antiguos"),
        MuggleArt(Type.Extracurricular, "Arte Muggle"),
        GhoulStudies(Type.Extracurricular, "Estudios Ghoul"),
        MagicalTheory(Type.Extracurricular, "Teoría Mágica"),
        DarkArts(Type.Extracurricular, "Artes Oscuras"),
        Fly(Type.Extracurricular, "Vuelo");

        public enum Type
        {
            Core,        //Troncal
            Elective,       //Optativa
            Extracurricular //Extracurricular
        }
        private Type type;
        private String inSpanish;

        Subject(Type type, String inSpanish)
        {
            this.inSpanish = inSpanish;
            this.type = type;
        }

        public String ToSpanish() {return inSpanish;}
        public String GetType() {return type.toString();}
        public Boolean CompareSpanishSubject(String subjectName)
        {
            return subjectName.equals(inSpanish);
        }

    }

    public Book(int code, String title, String author, String editorial, Subject subject, BookState state, int isAvailable) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.subject = subject;
        this.state = state;
        this.isAvailable = isAvailable >0 ? true : false;
    }

    public Book(int code, String title, String author, String editorial, Subject subject, BookState state, boolean isAvailable) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.subject = subject;
        this.state = state;
        this.isAvailable = isAvailable;
    }

    public int getCode() {
        return code;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEditorial() {
        return editorial;
    }

    public Subject getSubject(){return subject;}
    public String getSubjectString() {
        return subject.inSpanish;
    }
    public String getSubjectType() {return subject.type.toString();}

    public Subject.Type GetSubjectTypePlain(){return subject.type;}
    public static Subject TransformToSubject(String subjectName)
    {
        for (Subject subject : Subject.values()) {
            if(subjectName.equals(subject.inSpanish)) return subject;
        }
        return Subject.MuggleStudies;
    }

    public BookState getState(){return state;}
    public String getStateString() {
        return state.inSpanish;
    }
    public static BookState TransformToBookState(String stateName)
    {
        for (BookState subject : BookState.values()) {
            if(stateName.equals(subject.inSpanish)) return subject;
        }
        return BookState.Used;
    }

    public BookState GetBookState()
    {
        return state;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public int getAvailableInt()
    {
        return isAvailable ? 1 : 0;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void UpdateData(String title, String author, String editorial, Subject subject, BookState state)
    {
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.subject = subject;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Book{" +
                "code=" + code +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", editorial='" + editorial + '\'' +
                ", subject=" + subject +
                ", state=" + state +
                '}';
    }
}
