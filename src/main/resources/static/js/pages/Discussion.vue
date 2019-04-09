<template>
    <v-container fluid grid-list-xs>
        <v-layout justify-center row wrap>
            <slot v-for="board in boards">
            <v-flex d-flex xs4>
                <v-card class="my-1 mx-1">
                    <v-card-title primary class="headline">{{ board.name }}</v-card-title>
                    <v-card-text>
                    </v-card-text>
                </v-card>
            </v-flex>
            </slot>
            <v-flex shrink xs4 text-xs-center>
                <v-card class="my-1 mx-1">
                    <v-card-title primary class="headline">Create your own board
                        <v-spacer></v-spacer>
                        <v-btn color="success" @click="addNewBoard">
                            <v-icon>add</v-icon>
                        </v-btn>
                    </v-card-title>
                    <v-card-text>
                        <v-text-field placeholder="Type your board's name"
                        v-model="newBoard.name">
                        </v-text-field>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions, mapGetters } from 'vuex'
    export default {
        data() {
            return {
                newBoard: { name: '' },
                boards: []
            }
        },
        computed: {
            ...mapGetters(['getBoards'])
        },
        created () {
            this.boards = this.getBoards
        },
        methods: {
            ...mapActions(['addBoardAction']),
            addNewBoard() {
                if (!this.newBoard.name) { return; }
                this.addBoardAction(this.newBoard)
                this.newBoard.name = ''
            }
        }
    }
</script>