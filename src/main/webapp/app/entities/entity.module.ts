import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FinapplicationTipoContaFinappModule } from './tipo-conta/tipo-conta-finapp.module';
import { FinapplicationLancamentoFinappModule } from './lancamento/lancamento-finapp.module';
import { FinapplicationEntidadeReceitaDespesaFinappModule } from './entidade-receita-despesa/entidade-receita-despesa-finapp.module';
import { FinapplicationCategoriaFinappModule } from './categoria/categoria-finapp.module';
import { FinapplicationContaFinappModule } from './conta/conta-finapp.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FinapplicationTipoContaFinappModule,
        FinapplicationLancamentoFinappModule,
        FinapplicationEntidadeReceitaDespesaFinappModule,
        FinapplicationCategoriaFinappModule,
        FinapplicationContaFinappModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationEntityModule {}
