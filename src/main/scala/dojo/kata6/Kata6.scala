package dojo.kata6

trait Kata6 {
	val LIMIT = 28123 // 10000 // 1000
	
	def allAbundants:IndexedSeq[Int]
	def notExpressableAsSumOfAbundantNumbers: Set[Int]
}