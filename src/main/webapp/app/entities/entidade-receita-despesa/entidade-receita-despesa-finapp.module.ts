import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinapplicationSharedModule } from '../../shared';
import { FinapplicationAdminModule } from '../../admin/admin.module';
import {
    EntidadeReceitaDespesaFinappService,
    EntidadeReceitaDespesaFinappPopupService,
    EntidadeReceitaDespesaFinappComponent,
    EntidadeReceitaDespesaFinappDetailComponent,
    EntidadeReceitaDespesaFinappDialogComponent,
    EntidadeReceitaDespesaFinappPopupComponent,
    EntidadeReceitaDespesaFinappDeletePopupComponent,
    EntidadeReceitaDespesaFinappDeleteDialogComponent,
    entidadeReceitaDespesaRoute,
    entidadeReceitaDespesaPopupRoute,
    EntidadeReceitaDespesaFinappResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...entidadeReceitaDespesaRoute,
    ...entidadeReceitaDespesaPopupRoute,
];

@NgModule({
    imports: [
        FinapplicationSharedModule,
        FinapplicationAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntidadeReceitaDespesaFinappComponent,
        EntidadeReceitaDespesaFinappDetailComponent,
        EntidadeReceitaDespesaFinappDialogComponent,
        EntidadeReceitaDespesaFinappDeleteDialogComponent,
        EntidadeReceitaDespesaFinappPopupComponent,
        EntidadeReceitaDespesaFinappDeletePopupComponent,
    ],
    entryComponents: [
        EntidadeReceitaDespesaFinappComponent,
        EntidadeReceitaDespesaFinappDialogComponent,
        EntidadeReceitaDespesaFinappPopupComponent,
        EntidadeReceitaDespesaFinappDeleteDialogComponent,
        EntidadeReceitaDespesaFinappDeletePopupComponent,
    ],
    providers: [
        EntidadeReceitaDespesaFinappService,
        EntidadeReceitaDespesaFinappPopupService,
        EntidadeReceitaDespesaFinappResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationEntidadeReceitaDespesaFinappModule {}
