public class AshCPUMain {

	static const R1 = 0, 
		R2 = 1,
		R3 = 2,
		ADD = 3,
		MUL = 4,
		DIV = 5,
		SUB = 6,
		CMP = 7,
		JMP = 8,
		JNE = 9,
		JEQ = 10,
		JLT = 11,
		JGT = 12,
		JLE = 13,
		JGE = 14,
		HLT = 15
		
	static const PROGRAM = [
		R1, -10,
		CMP, 0,
		JGT, 11,
		JLT, 14,
		R1, 0,
		HLT,
		R1, 1,
		HLT,
		R1, -1
	]

	static var r1 = 0, r2 = 0
	static var pc = 0, cmp = 0

	public static func main(args : String[]) {
		while pc < PROGRAM.length {
			const opcode = fetch()
			eval(opcode)
		}
		System.out.println(String.valueOf(r1))
	}
	
	public static func fetch() : int {
		var opcode = PROGRAM[pc]
		pc = pc + 1
		return opcode
	}
	
	public static func eval(opcode : int) {
		match opcode {
			R1 -> r1 = fetch()
			R2 -> r2 = fetch()
			ADD -> r1 = r1 + r2
			SUB -> r1 = r1 - r2
			MUL -> r1 = r1 * r2
			DIV -> r1 = r1 / r2
			CMP {
				const operand = fetch()
				if r1 < operand {
					cmp = -1
				} else if r1 > operand {
					cmp = 1
				} else cmp = 0
			}
			JMP -> pc = fetch()
			JNE {
				const dest = fetch()
				pc = (cmp == 0 ? pc : dest)
			}
			JEQ {
				const dest2 = fetch()
				pc = (cmp == 0 ? dest2 : pc)
			}
			JLT {
				const dest3 = fetch()
				pc = (cmp == -1 ? dest3 : pc)
			}
			JGT {
				const dest4 = fetch()
				pc = (cmp == 1 ? dest4 : pc)
			}
			JLE {
				const dest5 = fetch()
				pc = (cmp == 1 ? pc : dest5)
			}
			JGE {
				const dest6 = fetch()
				pc = (cmp == -1 ? pc : dest6)
			}
			HLT -> pc = PROGRAM.length
			_ -> System.out.println("Invalid opcode")
		}
	}

}