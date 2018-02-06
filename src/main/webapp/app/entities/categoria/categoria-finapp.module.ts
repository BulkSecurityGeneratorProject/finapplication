import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FinapplicationSharedModule } from '../../shared';
import { FinapplicationAdminModule } from '../../admin/admin.module';
import {
    CategoriaFinappService,
    CategoriaFinappPopupService,
    CategoriaFinappComponent,
    CategoriaFinappDetailComponent,
    CategoriaFinappDialogComponent,
    CategoriaFinappPopupComponent,
    CategoriaFinappDeletePopupComponent,
    CategoriaFinappDeleteDialogComponent,
    categoriaRoute,
    categoriaPopupRoute,
    CategoriaFinappResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...categoriaRoute,
    ...categoriaPopupRoute,
];

@NgModule({
    imports: [
        FinapplicationSharedModule,
        FinapplicationAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CategoriaFinappComponent,
        CategoriaFinappDetailComponent,
        CategoriaFinappDialogComponent,
        CategoriaFinappDeleteDialogComponent,
        CategoriaFinappPopupComponent,
        CategoriaFinappDeletePopupComponent,
    ],
    entryComponents: [
        CategoriaFinappComponent,
        CategoriaFinappDialogComponent,
        CategoriaFinappPopupComponent,
        CategoriaFinappDeleteDialogComponent,
        CategoriaFinappDeletePopupComponent,
    ],
    providers: [
        CategoriaFinappService,
        CategoriaFinappPopupService,
        CategoriaFinappResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FinapplicationCategoriaFinappModule {}
