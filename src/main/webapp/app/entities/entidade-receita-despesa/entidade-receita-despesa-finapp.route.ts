import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EntidadeReceitaDespesaFinappComponent } from './entidade-receita-despesa-finapp.component';
import { EntidadeReceitaDespesaFinappDetailComponent } from './entidade-receita-despesa-finapp-detail.component';
import { EntidadeReceitaDespesaFinappPopupComponent } from './entidade-receita-despesa-finapp-dialog.component';
import {
    EntidadeReceitaDespesaFinappDeletePopupComponent
} from './entidade-receita-despesa-finapp-delete-dialog.component';

@Injectable()
export class EntidadeReceitaDespesaFinappResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const entidadeReceitaDespesaRoute: Routes = [
    {
        path: 'entidade-receita-despesa-finapp',
        component: EntidadeReceitaDespesaFinappComponent,
        resolve: {
            'pagingParams': EntidadeReceitaDespesaFinappResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.entidadeReceitaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entidade-receita-despesa-finapp/:id',
        component: EntidadeReceitaDespesaFinappDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.entidadeReceitaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entidadeReceitaDespesaPopupRoute: Routes = [
    {
        path: 'entidade-receita-despesa-finapp-new',
        component: EntidadeReceitaDespesaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.entidadeReceitaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entidade-receita-despesa-finapp/:id/edit',
        component: EntidadeReceitaDespesaFinappPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.entidadeReceitaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entidade-receita-despesa-finapp/:id/delete',
        component: EntidadeReceitaDespesaFinappDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'finapplicationApp.entidadeReceitaDespesa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
