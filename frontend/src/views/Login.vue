<template>
  <v-container
    fill-height
    fluid
    grid-list-xl>
    <v-layout
      justify-center
      wrap
    >
      <v-flex
        xs12
        md8
      >
        <material-card
          color="green"
          title="Login"
          text="Login to your account"
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md6>
                    <v-layout wrap>
                        <v-flex xs12>
                        <v-text-field
                            label="Email Address"
                            class="green-input"/>
                        </v-flex>
                        <v-flex xs12>
                        <v-text-field
                            type="password"
                            label="Password"
                            class="green-input"/>
                        </v-flex>
                    </v-layout>               
                    <v-flex xs12>
                        <router-link to="/sign-up">
                            Don't have an account yet ?
                        </router-link>
                    </v-flex>
                    <v-flex xs12>
                        <router-link to="/sign-up">
                            Forgot password ?
                        </router-link>
                    </v-flex>
                    <v-flex
                    xs12
                    text-xs-right
                    >
                    <v-btn
                        class="mx-0 font-weight-light"
                        color="success"
                        block
                    >
                        Login
                    </v-btn>
                    </v-flex> 
                </v-flex>
                <v-divider v-if="!responsive" vertical="true"></v-divider>
                <v-flex xs12 md5>
                    <v-layout wrap>
                        <v-flex xs12>
                        <v-btn
                            class="mx-0 font-weight-light"
                            color="danger"
                            block
                        >
                            Login with Google
                        </v-btn>
                        </v-flex>
                        <v-flex xs12>
                        <v-btn
                            block
                            class="mx-0 font-weight-light"
                            color="info"
                        >
                            Login with Facebook
                        </v-btn>
                        </v-flex>
                    </v-layout>
                </v-flex>

              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  //
}
</script>
