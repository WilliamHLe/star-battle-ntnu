package group16.project.game.ecs

import com.badlogic.ashley.core.Entity
import group16.project.game.ecs.utils.ComponentMapper

fun lerp(start: Float, end: Float, n: Float): Float {
    return (1 - n) * start + n * end
}

fun compareEntityByPosition(e1: Entity, e2: Entity): Int {
    val mapper = ComponentMapper.position
    val z1 = mapper[e1]?.z?:0f
    val z2 = mapper[e2]?.z?:0f
    return z1.compareTo(z2)
}

fun <T1, T2, T3> notNull(t1: T1?, t2: T2?, body: (T1, T2) -> T3): T3? =
        if (t1 != null && t2 != null)
            body(t1, t2)
        else null

fun <T1, T2, T3, T4> notNull(t1: T1?, t2: T2?, t3: T3?, body: (T1, T2, T3) -> T4): T4? =
        if (t1 != null && t2 != null && t3 != null)
            body(t1, t2, t3)
        else null