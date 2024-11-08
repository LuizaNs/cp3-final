package com.example.cp3.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.cp3.element.Pokemon

class MyDataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "pokemon.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "pokemon"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "nome"
        private const val COLUMN_TYPE = "tipo"
        private const val COLUMN_ABILITY = "habilidade"
        private const val COLUMN_WEAKNESS = "fraqueza"
        private const val COLUMN_HP = "hp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_TYPE TEXT, " +
                "$COLUMN_ABILITY TEXT, " +
                "$COLUMN_WEAKNESS TEXT, " +
                "$COLUMN_HP INTEGER)")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insira o Pokemon
    fun insertPokemon(pokemon: Pokemon): Long {
        return try {
            val db = this.writableDatabase
            val contentValues = ContentValues().apply {
                put(COLUMN_NAME, pokemon.nome)
                put(COLUMN_TYPE, pokemon.tipo)
                put(COLUMN_ABILITY, pokemon.habilidade)
                put(COLUMN_WEAKNESS, pokemon.fraqueza)
                put(COLUMN_HP, pokemon.hp)
            }
            db.insert(TABLE_NAME, null, contentValues)
        } catch (e: Exception) {
            Log.e("MyDataBase", "Erro ao adicionar Pokemon", e)
            -1 // Retorna um valor de erro
        }
    }

    // Listar todos os Pokémons
    fun getAllPokemon(): List<Pokemon> {
        val pokemonList = mutableListOf<Pokemon>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val pokemon = Pokemon(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        habilidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ABILITY)),
                        fraqueza = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEAKNESS)),
                        hp = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HP))
                    )
                    pokemonList.add(pokemon)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return pokemonList
    }

    // Obter um Pokémon pelo ID
    fun getPokemonById(pokemonId: Int): Pokemon? {
        val db = this.readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(pokemonId.toString()),
            null,
            null,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return Pokemon(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                    habilidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ABILITY)),
                    fraqueza = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEAKNESS)),
                    hp = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HP))
                )
            }
        }
        return null
    }

    // Atualizar Pokémon
    fun updatePokemon(pokemon: Pokemon): Boolean {
        return try {
            val db = this.writableDatabase
            val contentValues = ContentValues().apply {
                put(COLUMN_NAME, pokemon.nome)
                put(COLUMN_TYPE, pokemon.tipo)
                put(COLUMN_ABILITY, pokemon.habilidade)
                put(COLUMN_WEAKNESS, pokemon.fraqueza)
                put(COLUMN_HP, pokemon.hp)
            }
            val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(pokemon.id.toString()))
            result > 0 // Retorna true se a atualização foi bem-sucedida
        } catch (e: Exception) {
            Log.e("MyDataBase", "Erro ao atualizar Pokémon", e)
            false
        }
    }

    // Excluir Pokémon
    fun deletePokemon(pokemonId: Int): Boolean {
        return try {
            val db = this.writableDatabase
            val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(pokemonId.toString()))
            result > 0 // Retorna true se a exclusão foi bem-sucedida
        } catch (e: Exception) {
            Log.e("MyDataBase", "Erro ao excluir Pokémon", e)
            false
        }
    }
}
