package BackEnd;

import Assembly.*;

import java.util.HashSet;

public class LivenessAnalyzer {
    public static void visit(AsmRoot root) {
        root.functions.forEach(LivenessAnalyzer::analysis);
    }

    public static void analysis(AsmFunction function) {
        function.getDefUse(); //also initialized block.in = use
        //int times = 0;
        while (true) {
            boolean changed = false;
            for (var block : function.blocks) {
                int originInSize = block.in.size();
                int originOutSize = block.out.size();
                //update out[n] <- in[n.success]
                for (AsmBlock success : block.next) {
                    block.out.addAll(success.in);
                }
                //update in[n] <- use[n] & (out[n] - def[n])
                var tmp = new HashSet<>(block.out);
                tmp.removeAll(block.def); //out itself may contain def
                block.in.addAll(tmp);
                if (originInSize != block.in.size() || originOutSize != block.out.size()) changed = true;
            }
            if (!changed) return;
        }
        //System.err.println("Liveness analysis loop times reached " + times);
    }
}
