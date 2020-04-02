public class Test {

    public static void main(String[] args) {


    }

    public void r(){
        IRien r = new Rien();
    }

    interface IRien{

        public void rien();
    }

    class Rien implements IRien{

        private String rien;

        public String r(){
            return "lalala";
        }

        @Override
        public void rien() {

        }
    }
}
