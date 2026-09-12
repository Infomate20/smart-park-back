# Graph Report - smart-park  (2026-08-06)

## Corpus Check
- 173 files · ~68,562 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 5331 nodes · 16452 edges · 160 communities (154 shown, 6 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 1372 edges (avg confidence: 0.76)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b98dbb49`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- i
- chunk-2UMSGIQA.js
- chunk-JLYFP63R.js
- main-OWKDLGZI.js
- chunk-NFK5QI6Q.js
- bc
- UtilisateurResponseDto
- CategorieResponseDto
- dp
- chunk-XUE47SUA.js
- .ngOnChanges
- TicketServiceImpl.java
- e
- setTimeout
- .subscribe
- .remove
- updateValueAndValidity
- get
- n
- tv
- a
- .get
- .ngOnDestroy
- chunk-DDEZEMF3.js
- .subscribe
- c
- O
- UtilisateurServiceImpl
- AgenceController
- ImmobilisationController
- Ri
- chunk-5VZHUNVY.js
- ErrorResponse
- ResourceNotFoundException
- Utilisateur
- Transaction
- .init
- _flushAnimations
- ReportController.java
- TransactionController
- v
- chunk-XMOQ7BMQ.js
- e
- InterventionController
- Immobilisation
- .focus
- ImmobilisationResponseDto
- apply
- AgenceServiceImpl
- TransactionServiceImpl
- .getMonth
- le
- av
- chunk-YEVIWNIL.js
- AgenceResponseDto
- attach
- Qa
- .updateStickyColumnStyles
- InterventionRequestDto
- TransactionResponseDto
- constructor
- .constructor
- update
- polyfills-B6TNHZQ6.js
- AgenceRepository
- contains
- InterventionServiceImpl
- Intervention
- chunk-HTANHSLM.js
- UtilisateurResponseDto.java
- Fm
- ze
- UtilisateurRequestDto
- AgenceRequestDto
- AuthController
- InterventionResponseDto
- .clone
- match
- OneTimePassword
- ci
- eo
- DataInitializer.java
- ImmobilisationRequestDto
- updateStickyColumns
- Mf
- command
- un
- chunk-W4AHS3DZ.js
- SecurityConfig.java
- JwtService
- CustomUserPrincipal
- TransactionUpdateDto
- B
- toString
- vi
- ._getCellFromElement
- constructor
- f_
- SchemaAdjustments
- run
- j
- get
- TypeTransaction
- ._markRadiosForCheck
- forEach
- Vs
- LoginRequest
- ci
- position
- ._bindTransitionEvents
- Md
- De
- AiQueryController.java
- JwtAuthenticationFilter
- AiQueryService
- onKeydown
- hideTooltip
- AuthResponse
- mvnw
- W
- CodeGenerationServiceImpl
- JwtAuthenticationEntryPoint.java
- toString
- SqlValidatorService
- wn
- AuthenticationException
- .processQuestion
- tu
- ChangementMotDePasseRequestDto
- OtpRequestDto
- Logiciel.java
- OtpValidationRequestDto
- SpaFallbackController.java
- NativeQueryService
- RefreshTokenRequest
- TransactionValidationDto
- ._markRadiosForCheck
- vi
- GroqService
- Yt
- StatusTransaction
- un
- SmartParkApplicationTests.java
- hideTooltip
- EtatImmobilisation
- .clone
- Mt
- SmartParkApplication
- B
- TransactionValidationDto
- LogicielRepository.java
- smartPark:smart-park
- appendComponent
- updatePosition
- Zo
- gi
- toString

## God Nodes (most connected - your core abstractions)
1. `i` - 1097 edges
2. `e` - 281 edges
3. `bc()` - 218 edges
4. `a()` - 130 edges
5. `vy()` - 129 edges
6. `n` - 115 edges
7. `m()` - 103 edges
8. `ResourceNotFoundException` - 95 edges
9. `dp()` - 94 edges
10. `v()` - 89 edges

## Surprising Connections (you probably didn't know these)
- `getEtats()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/chunk-5VZHUNVY.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `pl()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/main-OWKDLGZI.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `rh()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/main-OWKDLGZI.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `y_()` --indirect_call--> `Ql()`  [INFERRED]
  smart-park/src/main/resources/static/chunk-2UMSGIQA.js → smart-park/src/main/resources/static/main-OWKDLGZI.js
- `create()` --indirect_call--> `e`  [INFERRED]
  smart-park/src/main/resources/static/chunk-5VZHUNVY.js → smart-park/src/main/resources/static/chunk-JLYFP63R.js

## Import Cycles
- None detected.

## Communities (160 total, 6 thin omitted)

### Community 0 - "i"
Cohesion: 0.01
Nodes (3): i, Xu(), wu()

### Community 1 - "chunk-2UMSGIQA.js"
Cohesion: 0.01
Nodes (91): addSeconds(), ah(), applyRedirectCommands(), applyRedirectCreateUrlTree(), bc(), bv(), Cf(), children() (+83 more)

### Community 2 - "chunk-JLYFP63R.js"
Cohesion: 0.02
Nodes (188): cb(), eD(), qD(), requiredTrue(), Wd(), Yp(), ad(), add() (+180 more)

### Community 3 - "main-OWKDLGZI.js"
Cohesion: 0.02
Nodes (42): vh(), YE(), Km(), mv(), ac(), activate(), assignDefaults(), brighter() (+34 more)

### Community 4 - "chunk-NFK5QI6Q.js"
Cohesion: 0.03
Nodes (91): A_(), aa(), av(), c_(), ca(), ch(), Db(), eb() (+83 more)

### Community 5 - "bc"
Cohesion: 0.05
Nodes (101): _0(), b0(), bw(), C0(), d0(), Ew(), f0(), g0() (+93 more)

### Community 6 - "UtilisateurResponseDto"
Cohesion: 0.06
Nodes (18): DeleteMapping, GetMapping, Page, Pageable, PreAuthorize, PutMapping, RequestMapping, RequiredArgsConstructor (+10 more)

### Community 7 - "CategorieResponseDto"
Cohesion: 0.12
Nodes (19): CategorieController, DeleteMapping, GetMapping, Page, Pageable, PostMapping, PreAuthorize, PutMapping (+11 more)

### Community 8 - "dp"
Cohesion: 0.14
Nodes (17): createElement(), _destroyRipple(), fadeInRipple(), fadeOut(), fadeOutAll(), fadeOutAllNonPersistent(), fadeOutRipple(), _finishRippleTransition() (+9 more)

### Community 9 - "chunk-XUE47SUA.js"
Cohesion: 0.03
Nodes (47): xs(), animate(), At(), bs(), bt(), computeStyle(), constructor(), cs() (+39 more)

### Community 10 - ".ngOnChanges"
Cohesion: 0.04
Nodes (29): applyStyles(), applyToHost(), attachments(), backdropClick(), detachments(), en(), getConfig(), Ji() (+21 more)

### Community 11 - "TicketServiceImpl.java"
Cohesion: 0.06
Nodes (50): JpaRepository, PrePersist, Authentication, GetMapping, Operation, Page, Pageable, PostMapping (+42 more)

### Community 12 - "e"
Cohesion: 0.05
Nodes (39): 1. À corriger en priorité côté front, 2.1 Le piège principal, 2.2 Le parcours, en trois appels, 2.3 Durées et rafraîchissement, 2.4 Déconnexion, 2.5 Verrouillage de compte, 2.6 Première connexion, 2.7 En-tête (+31 more)

### Community 13 - "setTimeout"
Cohesion: 0.06
Nodes (3): hide(), zy(), du()

### Community 14 - ".subscribe"
Cohesion: 0.05
Nodes (65): _setupKeyHandler(), Ah(), al(), bl(), complete(), connect(), D(), Dh() (+57 more)

### Community 15 - ".remove"
Cohesion: 0.04
Nodes (22): ad(), addClass(), alignToElement(), appendChild(), bp(), lr(), Os(), R0() (+14 more)

### Community 16 - "updateValueAndValidity"
Cohesion: 0.07
Nodes (35): _anyControls(), _anyControlsDirty(), _anyControlsHaveStatus(), _anyControlsTouched(), _applyFormState(), at(), Cd(), disable() (+27 more)

### Community 17 - "get"
Cohesion: 0.17
Nodes (12): AllArgsConstructor, Data, NoArgsConstructor, LigneAmortissementDto, AmortissementServiceImpl, Override, Page, Pageable (+4 more)

### Community 18 - "n"
Cohesion: 0.04
Nodes (25): $h(), p_(), n, _h(), Mn(), Q(), ae(), deleteCategory() (+17 more)

### Community 19 - "tv"
Cohesion: 0.16
Nodes (19): AmortissementController, GetMapping, Operation, Page, Pageable, PreAuthorize, RequestMapping, RequiredArgsConstructor (+11 more)

### Community 20 - "a"
Cohesion: 0.07
Nodes (91): Ac(), D_(), e0(), fw(), gD(), hD(), nD(), oy() (+83 more)

### Community 21 - ".get"
Cohesion: 0.11
Nodes (20): addHandler(), attachComponentPortal(), decoratePreventDefault(), destroy(), getBaseHref(), _getComponentRootNode(), getGlobalEventTarget(), getUserAgent() (+12 more)

### Community 22 - ".ngOnDestroy"
Cohesion: 0.10
Nodes (6): afterClosed(), attachTemplatePortal(), cl(), dl(), setAttachedHost(), setDisposeFn()

### Community 23 - "chunk-DDEZEMF3.js"
Cohesion: 0.17
Nodes (12): BeforeAll, AllArgsConstructor, Data, NoArgsConstructor, TransactionRequestDto, TypeTransaction, AFFECTATION, DESAFFECTATION (+4 more)

### Community 24 - ".subscribe"
Cohesion: 0.05
Nodes (10): createSubscription(), fs(), my(), skipPredicate(), withAllowedModifierKeys(), withHomeAndEnd(), withHorizontalOrientation(), withPageUpDown() (+2 more)

### Community 25 - "c"
Cohesion: 0.03
Nodes (39): ap(), composeAsync(), qv(), recognize(), Rf(), Sf(), sm(), wm() (+31 more)

### Community 26 - "O"
Cohesion: 0.07
Nodes (57): Ks(), nullValidator(), create(), findTransactions(), allowOnlyTimelineStyles(), appendInstructionToTimeline(), _applyAnimationRefDelays(), applyEmptyStep() (+49 more)

### Community 27 - "UtilisateurServiceImpl"
Cohesion: 0.10
Nodes (10): ResourceNotFoundException, Override, Page, Pageable, PasswordEncoder, RequiredArgsConstructor, Service, Slf4j (+2 more)

### Community 28 - "AgenceController"
Cohesion: 0.13
Nodes (12): AgenceController, DeleteMapping, GetMapping, Page, Pageable, PostMapping, PreAuthorize, PutMapping (+4 more)

### Community 29 - "ImmobilisationController"
Cohesion: 0.15
Nodes (13): ImmobilisationController, DeleteMapping, GetMapping, Operation, Page, Pageable, PostMapping, PreAuthorize (+5 more)

### Community 30 - "Ri"
Cohesion: 0.05
Nodes (63): email(), tD(), xC(), _adjustIndex(), attach(), attachToViewContainerRef(), by(), clear() (+55 more)

### Community 31 - "chunk-5VZHUNVY.js"
Cohesion: 0.04
Nodes (19): _n(), o_(), create(), getEtats(), clearSessionAndRedirect(), getAccessToken(), loadUserSession(), logout() (+11 more)

### Community 32 - "ErrorResponse"
Cohesion: 0.12
Nodes (21): AccessDeniedException, ConstraintViolationException, ExceptionHandler, HttpMessageNotReadableException, HttpRequestMethodNotSupportedException, MethodArgumentNotValidException, MethodArgumentTypeMismatchException, MissingServletRequestParameterException (+13 more)

### Community 33 - "ResourceNotFoundException"
Cohesion: 0.13
Nodes (7): BusinessException, ImmobilisationServiceImpl, Override, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 34 - "Utilisateur"
Cohesion: 0.11
Nodes (16): Modifying, Role, ADMIN, AGENT, TECHNICIEN, AllArgsConstructor, Data, Entity (+8 more)

### Community 35 - "Transaction"
Cohesion: 0.13
Nodes (15): EtatTransaction, EN_ATTENTE, REJETEE, VALIDEE, AllArgsConstructor, Data, Entity, NoArgsConstructor (+7 more)

### Community 36 - ".init"
Cohesion: 0.06
Nodes (32): ab(), hp(), Ib(), ob(), ac(), cc(), cn(), ea() (+24 more)

### Community 37 - "_flushAnimations"
Cohesion: 0.11
Nodes (38): absorbOptions(), append(), _beforeAnimationBuild(), _buildAnimation(), create(), destroyActiveAnimationsForElement(), destroyInnerAnimations(), drainQueuedTransitions() (+30 more)

### Community 38 - "ReportController.java"
Cohesion: 0.11
Nodes (22): ResponseStatus, Authentication, PasswordEncoder, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+14 more)

### Community 39 - "TransactionController"
Cohesion: 0.10
Nodes (18): DeleteMapping, GetMapping, Page, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+10 more)

### Community 40 - "v"
Cohesion: 0.05
Nodes (52): DetailVehiculeController, DeleteMapping, GetMapping, Operation, Page, Pageable, PreAuthorize, PutMapping (+44 more)

### Community 41 - "chunk-XMOQ7BMQ.js"
Cohesion: 0.08
Nodes (4): _canBeEnabled(), contains(), tt(), wg()

### Community 42 - "e"
Cohesion: 0.08
Nodes (18): Dy(), As(), ch(), e, eh(), er(), Fc(), io() (+10 more)

### Community 43 - "InterventionController"
Cohesion: 0.22
Nodes (16): ApiResponse, ApiResponses, InterventionController, DeleteMapping, GetMapping, Operation, Page, PostMapping (+8 more)

### Community 44 - "Immobilisation"
Cohesion: 0.10
Nodes (13): Immobilisation, AllArgsConstructor, Data, Entity, NoArgsConstructor, Table, ImmobilisationRepository, Page (+5 more)

### Community 45 - ".focus"
Cohesion: 0.11
Nodes (3): Ce(), isTyping(), toggle()

### Community 46 - "ImmobilisationResponseDto"
Cohesion: 0.15
Nodes (7): ImmobilisationResponseDto, AllArgsConstructor, Data, NoArgsConstructor, ImmobilisationService, Page, Pageable

### Community 47 - "apply"
Cohesion: 0.13
Nodes (24): _addPanelClasses(), apply(), _applyPosition(), _calculateBoundingBoxRect(), _canFitWithFlexibleDimensions(), ef(), _getExactOverlayX(), _getExactOverlayY() (+16 more)

### Community 48 - "AgenceServiceImpl"
Cohesion: 0.17
Nodes (8): AgenceServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 49 - "TransactionServiceImpl"
Cohesion: 0.20
Nodes (9): ValidationException, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional (+1 more)

### Community 50 - ".getMonth"
Cohesion: 0.14
Nodes (9): PostMapping, Component, PasswordEncoder, RequiredArgsConstructor, UtilisateurMapper, AllArgsConstructor, Data, NoArgsConstructor (+1 more)

### Community 51 - "le"
Cohesion: 0.16
Nodes (10): CategorieMapper, Component, CategorieRequestDto, AllArgsConstructor, Data, NoArgsConstructor, MethodeAmortissement, DEGRESSIF (+2 more)

### Community 52 - "av"
Cohesion: 0.06
Nodes (28): gC(), ngAfterViewInit(), Oi(), pp(), st(), ut(), openDetailDialog(), Ae() (+20 more)

### Community 53 - "chunk-YEVIWNIL.js"
Cohesion: 0.18
Nodes (8): ImmobilisationMapper, Component, RequiredArgsConstructor, ImmobilisationRequestDto, AllArgsConstructor, Data, NoArgsConstructor, CodeGenerationService

### Community 54 - "AgenceResponseDto"
Cohesion: 0.13
Nodes (7): AgenceResponseDto, AllArgsConstructor, Data, NoArgsConstructor, AgenceService, Page, Pageable

### Community 55 - "attach"
Cohesion: 0.25
Nodes (8): Es(), Si(), As(), es(), je(), kt(), pi(), _validateStyleAst()

### Community 56 - "Qa"
Cohesion: 0.09
Nodes (6): cg(), EC(), fl(), og(), Qa(), sg()

### Community 57 - ".updateStickyColumnStyles"
Cohesion: 0.10
Nodes (3): Gm(), gs(), wa()

### Community 58 - "InterventionRequestDto"
Cohesion: 0.11
Nodes (20): InterventionMapper, Component, InterventionRequestDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionUpdateDto, AllArgsConstructor (+12 more)

### Community 59 - "TransactionResponseDto"
Cohesion: 0.09
Nodes (26): Be(), deleteImmo(), ei(), Ge(), ii(), loadAllData(), loadDependencies(), ngOnInit() (+18 more)

### Community 60 - "constructor"
Cohesion: 0.10
Nodes (14): Da(), _g(), ngAfterViewInit(), afterDismissed(), afterOpened(), closeWithAction(), constructor(), _dismissAfter() (+6 more)

### Community 61 - ".constructor"
Cohesion: 0.06
Nodes (38): activateChildRoutes(), appendAll(), applyChanges(), applyUpdate(), clearStickyPositioning(), cm(), copyFrom(), createSegmentGroup() (+30 more)

### Community 62 - "update"
Cohesion: 0.07
Nodes (36): Vo(), calculateArc(), calculateLabelPositions(), cloneData(), cr(), data(), defaultTooltipText(), formatDates() (+28 more)

### Community 63 - "polyfills-B6TNHZQ6.js"
Cohesion: 0.14
Nodes (27): at(), bt(), ct(), dt(), et(), Fe(), gt(), ht() (+19 more)

### Community 64 - "AgenceRepository"
Cohesion: 0.16
Nodes (11): Agence, AllArgsConstructor, Data, Entity, NoArgsConstructor, Table, AgenceRepository, Page (+3 more)

### Community 65 - "contains"
Cohesion: 0.08
Nodes (3): getAll(), keys(), toString()

### Community 66 - "InterventionServiceImpl"
Cohesion: 0.23
Nodes (8): InterventionServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 67 - "Intervention"
Cohesion: 0.17
Nodes (16): TypeIntervention, DESINSTALLATION, INSTALLATION, MAINTENANCE_CORRECTIVE, MAINTENANCE_PREVENTIVE, Intervention, AllArgsConstructor, Data (+8 more)

### Community 68 - "chunk-HTANHSLM.js"
Cohesion: 0.19
Nodes (14): pw(), Ap(), bI(), Ci(), emit(), Np(), op(), runOutsideAngular() (+6 more)

### Community 69 - "UtilisateurResponseDto.java"
Cohesion: 0.16
Nodes (8): AmortissementTest, SpringBootTest, Test, Transactional, ImmobilisationSansNumeroSerieTest, SpringBootTest, Test, Transactional

### Community 70 - "Fm"
Cohesion: 0.07
Nodes (64): Ai(), b_(), bm(), Co(), cv(), Do(), em(), er() (+56 more)

### Community 71 - "ze"
Cohesion: 0.09
Nodes (28): Ai(), bi(), bs(), clamp(), De(), displayable(), formatHsl(), fr() (+20 more)

### Community 72 - "UtilisateurRequestDto"
Cohesion: 0.17
Nodes (12): Af(), compose(), hasAsyncValidator(), Mf(), pattern(), pf(), Qs(), rD() (+4 more)

### Community 73 - "AgenceRequestDto"
Cohesion: 0.13
Nodes (6): AgenceMapper, Component, AgenceRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 74 - "AuthController"
Cohesion: 0.17
Nodes (9): AuthController, GetMapping, HttpServletRequest, PostMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity, RestController (+1 more)

### Community 75 - "InterventionResponseDto"
Cohesion: 0.17
Nodes (7): InterventionResponseDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionService, Page, Pageable

### Community 76 - ".clone"
Cohesion: 0.08
Nodes (15): clampDate(), compareDate(), compareTime(), deserialize(), eo(), getValidDateOrNull(), hh(), jg() (+7 more)

### Community 77 - "match"
Cohesion: 0.17
Nodes (23): capture(), cc(), consumeOptional(), ev(), Ho(), match(), noMatchError(), parse() (+15 more)

### Community 78 - "OneTimePassword"
Cohesion: 0.14
Nodes (15): JavaMailSender, Entity, Getter, Setter, Table, OneTimePassword, OneTimePasswordRepository, EmailService (+7 more)

### Community 79 - "ci"
Cohesion: 0.08
Nodes (32): detectContentTypeHeader(), expandSegmentAgainstRouteUsingRedirect(), gb(), getChildConfig(), Gv(), hasChildren(), hm(), im() (+24 more)

### Community 80 - "eo"
Cohesion: 0.25
Nodes (8): CategorieServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 81 - "DataInitializer.java"
Cohesion: 0.12
Nodes (17): CommandLineRunner, DataInitializer, Component, Override, PasswordEncoder, RequiredArgsConstructor, Slf4j, Categorie (+9 more)

### Community 82 - "ImmobilisationRequestDto"
Cohesion: 0.11
Nodes (10): Qe(), containsElement(), getParentElement(), Ms(), n, normalizePropertyName(), qe(), query() (+2 more)

### Community 83 - "updateStickyColumns"
Cohesion: 0.18
Nodes (13): _addStickyStyle(), _getCalculatedZIndex(), _getCellWidths(), _getStickyEndColumnPositions(), _getStickyStartColumnPositions(), Py(), _removeStickyStyle(), _retrieveElementSize() (+5 more)

### Community 84 - "Mf"
Cohesion: 0.18
Nodes (10): Be(), fi(), _getPlayer(), Gt(), Ht(), jt(), listen(), process() (+2 more)

### Community 85 - "command"
Cohesion: 0.14
Nodes (20): beforeDestroy(), _buildPlayer(), command(), _convertKeyframesToObject(), finish(), hasStarted(), init(), _onFinish() (+12 more)

### Community 86 - "un"
Cohesion: 0.20
Nodes (9): by(), jm(), vy(), yy(), zm(), Mm(), Rl(), _y() (+1 more)

### Community 87 - "chunk-W4AHS3DZ.js"
Cohesion: 0.04
Nodes (52): am(), Fm(), fy(), getCookie(), Gy(), lm(), nm(), uy() (+44 more)

### Community 88 - "SecurityConfig.java"
Cohesion: 0.21
Nodes (13): AuthenticationConfiguration, AuthenticationProvider, Bean, Configuration, CorsConfigurationSource, EnableMethodSecurity, EnableWebSecurity, HttpSecurity (+5 more)

### Community 89 - "JwtService"
Cohesion: 0.22
Nodes (5): Claims, Key, Service, UserDetails, JwtService

### Community 90 - "CustomUserPrincipal"
Cohesion: 0.19
Nodes (8): GrantedAuthority, CustomUserDetailsService, CustomUserPrincipal, Override, RequiredArgsConstructor, Service, UserDetails, UserDetailsService

### Community 91 - "TransactionUpdateDto"
Cohesion: 0.38
Nodes (6): AuthServiceImpl, AuthenticationManager, Override, RequiredArgsConstructor, Service, Transactional

### Community 92 - "B"
Cohesion: 0.08
Nodes (32): addControl(), _adjustIndex(), al(), _allControlsDisabled(), asyncValidator(), _calculateStatus(), _cancelExistingSubscription(), Fd() (+24 more)

### Community 93 - "toString"
Cohesion: 0.09
Nodes (23): addHeaderEntry(), _assignAsyncValidators(), _assignValidators(), attachAnchors(), _canClose(), constructor(), _createAnchor(), enabled() (+15 more)

### Community 94 - "vi"
Cohesion: 0.15
Nodes (11): AI assistant (`assistance/`), Architecture, Auth (two-step, JWT), Commands, Configuration, Domain, Errors, graphify (+3 more)

### Community 95 - "._getCellFromElement"
Cohesion: 0.11
Nodes (4): mw(), Rg(), Zd(), Qd()

### Community 96 - "constructor"
Cohesion: 0.12
Nodes (20): bh(), bt(), cl(), constructor(), delete(), dt(), Gd(), generateColorScheme() (+12 more)

### Community 97 - "f_"
Cohesion: 0.18
Nodes (16): Bt(), eh(), Ei(), f_(), ih(), lh(), nh(), oh() (+8 more)

### Community 98 - "SchemaAdjustments"
Cohesion: 0.39
Nodes (5): DataSource, JdbcTemplate, SpringBootTest, Test, MigrationDepuisZeroTest

### Community 99 - "run"
Cohesion: 0.11
Nodes (23): clone(), Ae(), at(), Bt(), ec(), Ft(), Ge(), gh() (+15 more)

### Community 100 - "j"
Cohesion: 0.10
Nodes (23): create(), findTickets(), ah(), copy(), Ct(), eo(), ic(), ih() (+15 more)

### Community 102 - "get"
Cohesion: 0.13
Nodes (23): afterFlush(), afterFlushAnimationsDone(), _balanceNamespaceList(), clearElementCache(), collectEnterElement(), createNamespace(), createRenderer(), Ct() (+15 more)

### Community 103 - "TypeTransaction"
Cohesion: 0.09
Nodes (19): addAsyncValidators(), addValidators(), nw(), af(), ay(), em(), ey(), go() (+11 more)

### Community 104 - "._markRadiosForCheck"
Cohesion: 0.36
Nodes (4): EtiquetteService, EtiquetteImpl, RequiredArgsConstructor, Service

### Community 105 - "forEach"
Cohesion: 0.23
Nodes (12): clear(), deselect(), _getConcreteValue(), _hasQueuedChanges(), hasValue(), isSelected(), _markSelected(), select() (+4 more)

### Community 106 - "Vs"
Cohesion: 0.14
Nodes (14): Al(), _c(), createElement(), fl(), Gc(), Hs(), ml(), On() (+6 more)

### Community 107 - "LoginRequest"
Cohesion: 0.48
Nodes (5): AllArgsConstructor, Builder, Data, NoArgsConstructor, LoginRequest

### Community 108 - "ci"
Cohesion: 0.12
Nodes (15): activate(), activateRoutes(), ci(), deactivateChildRoutes(), deactivateRouteAndItsChildren(), deactivateRouteAndOutlet(), deactivateRoutes(), detachAndStoreRouteSubtree() (+7 more)

### Community 109 - "position"
Cohesion: 0.17
Nodes (12): Mo(), calculateHorizontalAlignment(), calculateHorizontalCaret(), calculateVerticalAlignment(), calculateVerticalCaret(), checkFlip(), determinePlacement(), onWindowResize() (+4 more)

### Community 110 - "._bindTransitionEvents"
Cohesion: 0.17
Nodes (12): constructor(), destroy(), finish(), hasStarted(), init(), onDestroy(), onDone(), _onFinish() (+4 more)

### Community 111 - "Md"
Cohesion: 0.14
Nodes (15): dd(), Ff(), iD(), Md(), minLength(), registerOnChange(), _registerOnDestroy(), registerOnDisabledChange() (+7 more)

### Community 112 - "De"
Cohesion: 0.60
Nodes (5): AllArgsConstructor, Builder, Data, NoArgsConstructor, RefreshTokenRequest

### Community 113 - "AiQueryController.java"
Cohesion: 0.30
Nodes (10): AiQueryController, AiQueryRequest, Getter, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+2 more)

### Community 114 - "JwtAuthenticationFilter"
Cohesion: 0.31
Nodes (9): FilterChain, OncePerRequestFilter, Component, HttpServletRequest, HttpServletResponse, Override, RequiredArgsConstructor, UserDetailsService (+1 more)

### Community 115 - "AiQueryService"
Cohesion: 0.31
Nodes (8): ObjectMapper, AiQueryService, Logger, RequiredArgsConstructor, Service, Slf4j, Component, PromptFactory

### Community 116 - "onKeydown"
Cohesion: 0.31
Nodes (13): _getItemsArray(), onKeydown(), _setActiveInDefaultMode(), _setActiveInWrapMode(), setActiveItem(), _setActiveItemByDelta(), _setActiveItemByIndex(), setFirstItemActive() (+5 more)

### Community 117 - "hideTooltip"
Cohesion: 0.05
Nodes (17): ay(), close(), disconnect(), _finishDialogClose(), mm(), setStyle(), Vd(), dismiss() (+9 more)

### Community 118 - "AuthResponse"
Cohesion: 0.60
Nodes (5): AuthResponse, AllArgsConstructor, Builder, Data, NoArgsConstructor

### Community 119 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 120 - "W"
Cohesion: 0.12
Nodes (17): av(), bv(), cu(), cv(), dv(), is(), iv(), lv() (+9 more)

### Community 121 - "CodeGenerationServiceImpl"
Cohesion: 0.36
Nodes (5): CodeGenerationServiceImpl, Override, RequiredArgsConstructor, Service, Slf4j

### Community 123 - "JwtAuthenticationEntryPoint.java"
Cohesion: 0.36
Nodes (7): AuthenticationEntryPoint, AuthenticationException, Component, HttpServletRequest, HttpServletResponse, Override, JwtAuthenticationEntryPoint

### Community 124 - "toString"
Cohesion: 0.70
Nodes (4): ImmobilisationLightDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 125 - "SqlValidatorService"
Cohesion: 0.38
Nodes (3): Pattern, Service, SqlValidatorService

### Community 126 - "wn"
Cohesion: 0.10
Nodes (22): ao(), Ce(), En(), Mh(), Mt(), Oe(), Oi(), oo() (+14 more)

### Community 127 - "AuthenticationException"
Cohesion: 0.38
Nodes (3): AuthenticationException, AuthenticationFailedException, InvalidTokenException

### Community 128 - ".processQuestion"
Cohesion: 0.83
Nodes (3): AiTrainingLog, AllArgsConstructor, Getter

### Community 129 - "tu"
Cohesion: 0.27
Nodes (10): _executeOnStable(), focusFirstTabbableElement(), focusFirstTabbableElementWhenReady(), focusInitialElement(), focusInitialElementWhenReady(), focusLastTabbableElement(), focusLastTabbableElementWhenReady(), _getFirstTabbableElement() (+2 more)

### Community 130 - "ChangementMotDePasseRequestDto"
Cohesion: 0.53
Nodes (4): ChangementMotDePasseRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 131 - "OtpRequestDto"
Cohesion: 0.83
Nodes (3): Getter, Setter, OtpRequestDto

### Community 132 - "Logiciel.java"
Cohesion: 0.52
Nodes (6): AllArgsConstructor, Builder, Data, Entity, NoArgsConstructor, Logiciel

### Community 133 - "OtpValidationRequestDto"
Cohesion: 0.83
Nodes (3): Getter, Setter, OtpValidationRequestDto

### Community 134 - "SpaFallbackController.java"
Cohesion: 0.53
Nodes (4): Controller, Order, RequestMapping, SpaFallbackController

### Community 135 - "NativeQueryService"
Cohesion: 0.53
Nodes (4): EntityManager, Service, Transactional, NativeQueryService

### Community 136 - "RefreshTokenRequest"
Cohesion: 0.83
Nodes (3): AllArgsConstructor, Getter, LoginStep1ResponseDto

### Community 137 - "TransactionValidationDto"
Cohesion: 0.13
Nodes (7): PutMapping, Component, TransactionMapper, AllArgsConstructor, Data, NoArgsConstructor, TransactionUpdateDto

### Community 139 - "vi"
Cohesion: 0.14
Nodes (16): applyValueToInputSignal(), bg(), br(), ch(), cl(), consumerMarkedDirty(), _g(), gi() (+8 more)

### Community 140 - "GroqService"
Cohesion: 0.70
Nodes (4): RestTemplate, GroqService, Logger, Service

### Community 142 - "Yt"
Cohesion: 0.23
Nodes (3): path(), $u(), Yt()

### Community 143 - "StatusTransaction"
Cohesion: 0.40
Nodes (4): StatusTransaction, EN_ATTENTE, REJETEE, VALIDEE

### Community 144 - "un"
Cohesion: 0.17
Nodes (12): Gs(), appendChild(), di(), ec(), Fs(), gl(), insertBefore(), jl() (+4 more)

### Community 145 - "SmartParkApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, Test, SmartParkApplicationTests

### Community 146 - "hideTooltip"
Cohesion: 0.18
Nodes (11): addHideListeners(), createBoundOptions(), hideTooltip(), ngOnDestroy(), onBlur(), onFocus(), onMouseClick(), onMouseEnter() (+3 more)

### Community 147 - "EtatImmobilisation"
Cohesion: 0.25
Nodes (5): EtatImmobilisation, EN_PANNE, EN_REPARATION, EN_SERVICE, MISE_AU_REBUS

### Community 148 - ".clone"
Cohesion: 0.22
Nodes (3): append(), delete(), set()

### Community 151 - "B"
Cohesion: 0.43
Nodes (6): hf(), jw(), nt(), tx(), w0(), B()

### Community 152 - "TransactionValidationDto"
Cohesion: 0.70
Nodes (4): AllArgsConstructor, Data, NoArgsConstructor, TransactionValidationDto

### Community 155 - "appendComponent"
Cohesion: 0.40
Nodes (5): appendComponent(), getComponentRootNode(), getRootViewContainer(), getRootViewContainerNode(), projectComponentBindings()

### Community 156 - "updatePosition"
Cohesion: 0.08
Nodes (37): addPanelClass(), attach(), _attachBackdrop(), bottom(), centerHorizontally(), centerVertically(), _clearPanelClasses(), _completeDetachContent() (+29 more)

### Community 157 - "Zo"
Cohesion: 0.50
Nodes (4): Zo(), listen(), setProperty(), shouldReplay()

### Community 160 - "toString"
Cohesion: 0.22
Nodes (9): bc(), dh(), getColor(), getLinearGradientStops(), mr(), no(), toString(), uo() (+1 more)

## Knowledge Gaps
- **85 isolated node(s):** `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE`, `EN_REPARATION`, `MISE_AU_REBUS` (+80 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `i` connect `i` to `chunk-2UMSGIQA.js`, `chunk-JLYFP63R.js`, `main-OWKDLGZI.js`, `chunk-NFK5QI6Q.js`, `bc`, `dp`, `chunk-XUE47SUA.js`, `.ngOnChanges`, `._markRadiosForCheck`, `vi`, `._handleCalendarBodyKeydown`, `setTimeout`, `.remove`, `updateValueAndValidity`, `Yt`, `.subscribe`, `n`, `.clone`, `.get`, `.ngOnDestroy`, `Mt`, `.subscribe`, `c`, `B`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `toString`, `.init`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `e`, `.focus`, `apply`, `av`, `Qa`, `.updateStickyColumnStyles`, `TransactionResponseDto`, `constructor`, `.constructor`, `update`, `polyfills-B6TNHZQ6.js`, `contains`, `chunk-HTANHSLM.js`, `Fm`, `ze`, `UtilisateurRequestDto`, `.clone`, `ci`, `ImmobilisationRequestDto`, `updateStickyColumns`, `command`, `un`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `constructor`, `run`, `j`, `n`, `get`, `TypeTransaction`, `forEach`, `ci`, `position`, `._bindTransitionEvents`, `Md`, `hideTooltip`, `W`, `._focusActiveCell`, `wn`?**
  _High betweenness centrality (0.204) - this node is a cross-community bridge._
- **Why does `ResourceNotFoundException` connect `UtilisateurServiceImpl` to `TransactionValidationDto`, `TicketServiceImpl.java`, `get`, `ErrorResponse`, `ResourceNotFoundException`, `ReportController.java`, `v`, `Immobilisation`, `AgenceServiceImpl`, `TransactionServiceImpl`, `.getMonth`, `le`, `chunk-YEVIWNIL.js`, `InterventionServiceImpl`, `AgenceRequestDto`, `OneTimePassword`, `eo`, `TransactionUpdateDto`, `._markRadiosForCheck`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **Why does `e` connect `c` to `chunk-2UMSGIQA.js`, `tu`, `chunk-JLYFP63R.js`, `chunk-NFK5QI6Q.js`, `bc`, `dp`, `chunk-XUE47SUA.js`, `.ngOnChanges`, `vi`, `setTimeout`, `Yt`, `.remove`, `updateValueAndValidity`, `.subscribe`, `n`, `.clone`, `.get`, `.ngOnDestroy`, `B`, `.subscribe`, `a`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `.focus`, `apply`, `av`, `.updateStickyColumnStyles`, `TransactionResponseDto`, `.constructor`, `contains`, `Fm`, `UtilisateurRequestDto`, `.clone`, `ci`, `command`, `un`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `f_`, `run`, `get`, `TypeTransaction`, `Md`, `hideTooltip`, `W`, `._focusActiveCell`?**
  _High betweenness centrality (0.017) - this node is a cross-community bridge._
- **Are the 95 inferred relationships involving `i` (e.g. with `.forChild()` and `.forRoot()`) actually correct?**
  _`i` has 95 INFERRED edges - model-reasoned connections that need verification._
- **Are the 116 inferred relationships involving `e` (e.g. with `_addPanelClasses()` and `Af()`) actually correct?**
  _`e` has 116 INFERRED edges - model-reasoned connections that need verification._
- **Are the 118 inferred relationships involving `a()` (e.g. with `aa()` and `activateChildRoutes()`) actually correct?**
  _`a()` has 118 INFERRED edges - model-reasoned connections that need verification._
- **What connects `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE` to the rest of the system?**
  _85 weakly-connected nodes found - possible documentation gaps or missing edges._