package io.ruin.api

import kotlin.random.Random

fun random(of: Int) = nextInt(of) + 1

fun nextInt(upperBound: Int) = Random.nextInt(upperBound)