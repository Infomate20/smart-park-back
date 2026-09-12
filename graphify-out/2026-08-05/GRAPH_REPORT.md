# Graph Report - smart-park  (2026-08-05)

## Corpus Check
- 171 files · ~65,199 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 5285 nodes · 16362 edges · 142 communities (135 shown, 7 thin omitted)
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
- j
- n
- get
- ._markRadiosForCheck
- forEach
- Vs
- ci
- position
- Md
- De
- AiQueryController.java
- JwtAuthenticationFilter
- AiQueryService
- onKeydown
- hideTooltip
- mvnw
- CodeGenerationServiceImpl
- JwtAuthenticationEntryPoint.java
- toString
- wn
- AuthenticationException
- .processQuestion
- tu
- ChangementMotDePasseRequestDto
- Logiciel.java
- SpaFallbackController.java
- NativeQueryService
- RefreshTokenRequest
- TransactionValidationDto
- eh
- GroqService
- StatusTransaction
- appendComponent
- SmartParkApplicationTests.java
- SmartParkApplication
- gi
- LogicielRepository.java
- smartPark:smart-park
- updatePosition
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
- `nw()` --indirect_call--> `on()`  [INFERRED]
  smart-park/src/main/resources/static/chunk-2UMSGIQA.js → smart-park/src/main/resources/static/chunk-JLYFP63R.js

## Import Cycles
- None detected.

## Communities (142 total, 7 thin omitted)

### Community 0 - "i"
Cohesion: 0.01
Nodes (4): i, Rg(), Xu(), wu()

### Community 1 - "chunk-2UMSGIQA.js"
Cohesion: 0.01
Nodes (103): _0(), addAsyncValidators(), addSeconds(), addValidators(), ah(), applyRedirectCommands(), applyRedirectCreateUrlTree(), attachAnchors() (+95 more)

### Community 2 - "chunk-JLYFP63R.js"
Cohesion: 0.02
Nodes (163): cb(), processChildren(), add(), _addAfter(), _addIdentityChange(), addMatch(), _addParent(), _addToMoves() (+155 more)

### Community 3 - "main-OWKDLGZI.js"
Cohesion: 0.02
Nodes (43): vh(), YE(), Km(), mv(), ac(), activate(), assignDefaults(), brighter() (+35 more)

### Community 4 - "chunk-NFK5QI6Q.js"
Cohesion: 0.03
Nodes (118): A_(), aa(), ab(), av(), b_(), c_(), ca(), ch() (+110 more)

### Community 5 - "bc"
Cohesion: 0.07
Nodes (88): Ac(), D_(), e0(), fw(), hD(), nC(), nD(), ct() (+80 more)

### Community 6 - "UtilisateurResponseDto"
Cohesion: 0.06
Nodes (19): DeleteMapping, GetMapping, Page, Pageable, PostMapping, PreAuthorize, PutMapping, RequestMapping (+11 more)

### Community 7 - "CategorieResponseDto"
Cohesion: 0.12
Nodes (20): CategorieController, CrossOrigin, DeleteMapping, GetMapping, Page, Pageable, PostMapping, PreAuthorize (+12 more)

### Community 8 - "dp"
Cohesion: 0.06
Nodes (86): bw(), cx(), d0(), Ew(), f0(), g0(), gD(), h0() (+78 more)

### Community 9 - "chunk-XUE47SUA.js"
Cohesion: 0.04
Nodes (45): animate(), Be(), computeStyle(), constructor(), _convertKeyframesToObject(), cs(), di(), disableAnimations() (+37 more)

### Community 10 - ".ngOnChanges"
Cohesion: 0.05
Nodes (20): attachments(), backdropClick(), detachments(), en(), getConfig(), keydownEvents(), Lf(), Oi() (+12 more)

### Community 11 - "TicketServiceImpl.java"
Cohesion: 0.06
Nodes (49): PrePersist, Authentication, GetMapping, Operation, Page, Pageable, PostMapping, PreAuthorize (+41 more)

### Community 12 - "e"
Cohesion: 0.13
Nodes (18): jm(), ad(), fh(), gn(), jg(), kE(), Mm(), Oi() (+10 more)

### Community 13 - "setTimeout"
Cohesion: 0.04
Nodes (14): _canClose(), close(), disconnect(), _finishDialogClose(), hide(), setStyle(), Vd(), dismiss() (+6 more)

### Community 14 - ".subscribe"
Cohesion: 0.05
Nodes (69): _setupKeyHandler(), Ah(), Ai(), al(), bl(), Bt(), Ci(), complete() (+61 more)

### Community 15 - ".remove"
Cohesion: 0.04
Nodes (17): addClass(), alignToElement(), appendChild(), applyChanges(), deactivateRouteAndOutlet(), dh(), ms(), Mt() (+9 more)

### Community 16 - "updateValueAndValidity"
Cohesion: 0.06
Nodes (54): addControl(), _adjustIndex(), _allControlsDisabled(), _anyControls(), _anyControlsDirty(), _anyControlsHaveStatus(), _anyControlsTouched(), _applyFormState() (+46 more)

### Community 17 - "get"
Cohesion: 0.15
Nodes (16): AllArgsConstructor, Data, NoArgsConstructor, LigneAmortissementDto, AllArgsConstructor, Data, NoArgsConstructor, PlanAmortissementDto (+8 more)

### Community 18 - "n"
Cohesion: 0.04
Nodes (18): at(), bt(), constructor(), deleteUser(), emailValidator(), gt(), ht(), LoadDependencies() (+10 more)

### Community 19 - "tv"
Cohesion: 0.18
Nodes (15): AmortissementController, GetMapping, Operation, Page, Pageable, PreAuthorize, RequestMapping, RequiredArgsConstructor (+7 more)

### Community 20 - "a"
Cohesion: 0.06
Nodes (44): Sf(), Zr(), As(), av(), bd(), Cd(), checkNoChanges(), cv() (+36 more)

### Community 21 - ".get"
Cohesion: 0.07
Nodes (35): addHandler(), decoratePreventDefault(), _destroyRipple(), detectContentTypeHeader(), fadeInRipple(), fadeOut(), fadeOutAll(), fadeOutAllNonPersistent() (+27 more)

### Community 22 - ".ngOnDestroy"
Cohesion: 0.05
Nodes (12): ad(), afterClosed(), append(), bp(), lr(), Os(), removeAttribute(), setAttribute() (+4 more)

### Community 23 - "chunk-DDEZEMF3.js"
Cohesion: 0.18
Nodes (6): Component, TransactionMapper, AllArgsConstructor, Data, NoArgsConstructor, TransactionRequestDto

### Community 24 - ".subscribe"
Cohesion: 0.04
Nodes (9): createSubscription(), skipPredicate(), wg(), withAllowedModifierKeys(), withHomeAndEnd(), withHorizontalOrientation(), withPageUpDown(), withVerticalOrientation() (+1 more)

### Community 25 - "c"
Cohesion: 0.20
Nodes (8): AmortissementTest, SpringBootTest, Test, Transactional, ImmobilisationSansNumeroSerieTest, SpringBootTest, Test, Transactional

### Community 26 - "O"
Cohesion: 0.08
Nodes (52): create(), findTransactions(), allowOnlyTimelineStyles(), appendInstructionToTimeline(), _applyAnimationRefDelays(), applyEmptyStep(), applyStylesToKeyframe(), build() (+44 more)

### Community 27 - "UtilisateurServiceImpl"
Cohesion: 0.07
Nodes (18): ResourceNotFoundException, Component, PasswordEncoder, RequiredArgsConstructor, UtilisateurMapper, AllArgsConstructor, Data, NoArgsConstructor (+10 more)

### Community 28 - "AgenceController"
Cohesion: 0.13
Nodes (12): AgenceController, CrossOrigin, DeleteMapping, GetMapping, Page, Pageable, PreAuthorize, PutMapping (+4 more)

### Community 29 - "ImmobilisationController"
Cohesion: 0.11
Nodes (18): ImmobilisationController, CrossOrigin, DeleteMapping, GetMapping, Operation, Page, Pageable, PostMapping (+10 more)

### Community 30 - "Ri"
Cohesion: 0.05
Nodes (60): gC(), ob(), _adjustIndex(), af(), attach(), attachToViewContainerRef(), by(), clear() (+52 more)

### Community 31 - "chunk-5VZHUNVY.js"
Cohesion: 0.03
Nodes (39): _n(), o_(), yv(), create(), getEtats(), clearSessionAndRedirect(), getAccessToken(), loadUserSession() (+31 more)

### Community 32 - "ErrorResponse"
Cohesion: 0.11
Nodes (22): AccessDeniedException, ConstraintViolationException, ExceptionHandler, HttpMessageNotReadableException, HttpRequestMethodNotSupportedException, MethodArgumentNotValidException, MethodArgumentTypeMismatchException, MissingServletRequestParameterException (+14 more)

### Community 33 - "ResourceNotFoundException"
Cohesion: 0.13
Nodes (9): BusinessException, ImmobilisationServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j (+1 more)

### Community 34 - "Utilisateur"
Cohesion: 0.10
Nodes (17): Modifying, Override, Role, ADMIN, AGENT, TECHNICIEN, AllArgsConstructor, Data (+9 more)

### Community 35 - "Transaction"
Cohesion: 0.13
Nodes (15): EtatTransaction, EN_ATTENTE, REJETEE, VALIDEE, AllArgsConstructor, Data, Entity, NoArgsConstructor (+7 more)

### Community 36 - ".init"
Cohesion: 0.22
Nodes (11): ah(), eo(), ih(), ir(), nh(), Ns(), pi(), rc() (+3 more)

### Community 37 - "_flushAnimations"
Cohesion: 0.10
Nodes (40): absorbOptions(), append(), _beforeAnimationBuild(), _buildAnimation(), _buildPlayer(), containsElement(), create(), destroyActiveAnimationsForElement() (+32 more)

### Community 38 - "ReportController.java"
Cohesion: 0.11
Nodes (22): ResponseStatus, Authentication, PasswordEncoder, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+14 more)

### Community 39 - "TransactionController"
Cohesion: 0.18
Nodes (11): DeleteMapping, GetMapping, Page, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+3 more)

### Community 40 - "v"
Cohesion: 0.05
Nodes (52): DetailVehiculeController, DeleteMapping, GetMapping, Operation, Page, Pageable, PreAuthorize, PutMapping (+44 more)

### Community 41 - "chunk-XMOQ7BMQ.js"
Cohesion: 0.05
Nodes (6): _canBeEnabled(), contains(), ht(), tt(), zt(), ngAfterViewInit()

### Community 42 - "e"
Cohesion: 0.07
Nodes (25): Dy(), Ai(), An(), As(), br(), ch(), displayable(), e (+17 more)

### Community 43 - "InterventionController"
Cohesion: 0.21
Nodes (16): ApiResponse, ApiResponses, InterventionController, CrossOrigin, DeleteMapping, GetMapping, Operation, Page (+8 more)

### Community 44 - "Immobilisation"
Cohesion: 0.12
Nodes (15): Immobilisation, AllArgsConstructor, Data, Entity, NoArgsConstructor, Table, ImmobilisationRepository, Page (+7 more)

### Community 45 - ".focus"
Cohesion: 0.09
Nodes (3): Ce(), isTyping(), toggle()

### Community 46 - "ImmobilisationResponseDto"
Cohesion: 0.14
Nodes (7): ImmobilisationLightDto, AllArgsConstructor, Data, NoArgsConstructor, ImmobilisationService, Page, Pageable

### Community 47 - "apply"
Cohesion: 0.13
Nodes (25): apply(), _applyPosition(), _calculateBoundingBoxRect(), _canFitWithFlexibleDimensions(), Cd(), ef(), _getExactOverlayX(), _getExactOverlayY() (+17 more)

### Community 48 - "AgenceServiceImpl"
Cohesion: 0.16
Nodes (8): AgenceServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 49 - "TransactionServiceImpl"
Cohesion: 0.24
Nodes (8): Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional, TransactionServiceImpl

### Community 50 - ".getMonth"
Cohesion: 0.06
Nodes (31): pD(), Tt(), yt(), ngAfterViewInit(), Te(), ti(), ze(), fp() (+23 more)

### Community 51 - "le"
Cohesion: 0.16
Nodes (10): CategorieMapper, Component, CategorieRequestDto, AllArgsConstructor, Data, NoArgsConstructor, MethodeAmortissement, DEGRESSIF (+2 more)

### Community 52 - "av"
Cohesion: 0.04
Nodes (24): ap(), wm(), attachToAppRef(), Bn(), cs(), e, Fe(), Hp() (+16 more)

### Community 53 - "chunk-YEVIWNIL.js"
Cohesion: 0.16
Nodes (8): ImmobilisationMapper, Component, RequiredArgsConstructor, ImmobilisationRequestDto, AllArgsConstructor, Data, NoArgsConstructor, CodeGenerationService

### Community 54 - "AgenceResponseDto"
Cohesion: 0.13
Nodes (7): AgenceResponseDto, AllArgsConstructor, Data, NoArgsConstructor, AgenceService, Page, Pageable

### Community 55 - "attach"
Cohesion: 0.13
Nodes (15): Es(), lm(), qa(), As(), es(), je(), kt(), Mt() (+7 more)

### Community 56 - "Qa"
Cohesion: 0.10
Nodes (7): cg(), EC(), fl(), og(), Qa(), sg(), xC()

### Community 57 - ".updateStickyColumnStyles"
Cohesion: 0.10
Nodes (3): Gm(), gs(), wa()

### Community 58 - "InterventionRequestDto"
Cohesion: 0.09
Nodes (21): PostMapping, InterventionMapper, Component, InterventionRequestDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionUpdateDto (+13 more)

### Community 59 - "TransactionResponseDto"
Cohesion: 0.16
Nodes (7): AllArgsConstructor, Data, NoArgsConstructor, TransactionResponseDto, Page, Pageable, TransactionService

### Community 60 - "constructor"
Cohesion: 0.09
Nodes (17): Da(), _g(), ngAfterViewInit(), afterDismissed(), afterOpened(), be(), closeWithAction(), constructor() (+9 more)

### Community 61 - ".constructor"
Cohesion: 0.23
Nodes (3): path(), $u(), Yt()

### Community 62 - "update"
Cohesion: 0.07
Nodes (37): Vo(), Ad(), calculateArc(), calculateLabelPositions(), cloneData(), cr(), data(), defaultTooltipText() (+29 more)

### Community 63 - "polyfills-B6TNHZQ6.js"
Cohesion: 0.07
Nodes (43): constructor(), destroy(), finish(), hasStarted(), init(), onDestroy(), onDone(), _onFinish() (+35 more)

### Community 64 - "AgenceRepository"
Cohesion: 0.10
Nodes (13): AgenceMapper, Component, Agence, AllArgsConstructor, Data, Entity, NoArgsConstructor, Table (+5 more)

### Community 66 - "InterventionServiceImpl"
Cohesion: 0.23
Nodes (8): InterventionServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 67 - "Intervention"
Cohesion: 0.17
Nodes (16): TypeIntervention, DESINSTALLATION, INSTALLATION, MAINTENANCE_CORRECTIVE, MAINTENANCE_PREVENTIVE, Intervention, AllArgsConstructor, Data (+8 more)

### Community 68 - "chunk-HTANHSLM.js"
Cohesion: 0.08
Nodes (25): eb(), Np(), Se(), tE(), tp(), Xc(), re(), Ae() (+17 more)

### Community 69 - "UtilisateurResponseDto.java"
Cohesion: 0.22
Nodes (5): EtatImmobilisation, EN_PANNE, EN_REPARATION, EN_SERVICE, MISE_AU_REBUS

### Community 70 - "Fm"
Cohesion: 0.05
Nodes (79): _addPanelClasses(), Af(), Ai(), bm(), cm(), cv(), em(), er() (+71 more)

### Community 71 - "ze"
Cohesion: 0.11
Nodes (24): ao(), bi(), clamp(), En(), formatHsl(), gr(), Mh(), Mt() (+16 more)

### Community 72 - "UtilisateurRequestDto"
Cohesion: 0.09
Nodes (21): ay(), compose(), fc(), fs(), hasAsyncValidator(), jo(), Mf(), mm() (+13 more)

### Community 73 - "AgenceRequestDto"
Cohesion: 0.31
Nodes (5): PostMapping, AgenceRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 74 - "AuthController"
Cohesion: 0.07
Nodes (34): AuthController, CrossOrigin, GetMapping, HttpServletRequest, PostMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity (+26 more)

### Community 75 - "InterventionResponseDto"
Cohesion: 0.20
Nodes (7): InterventionResponseDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionService, Page, Pageable

### Community 76 - ".clone"
Cohesion: 0.08
Nodes (15): clampDate(), compareDate(), compareTime(), deserialize(), eo(), getValidDateOrNull(), hh(), jg() (+7 more)

### Community 77 - "match"
Cohesion: 0.17
Nodes (23): capture(), cc(), consumeOptional(), ev(), Ho(), match(), noMatchError(), parse() (+15 more)

### Community 78 - "OneTimePassword"
Cohesion: 0.13
Nodes (16): JavaMailSender, JpaRepository, Entity, Getter, Setter, Table, OneTimePassword, OneTimePasswordRepository (+8 more)

### Community 79 - "ci"
Cohesion: 0.12
Nodes (23): dv(), expandSegmentAgainstRouteUsingRedirect(), getChildConfig(), Gv(), hasChildren(), hm(), im(), Kt() (+15 more)

### Community 80 - "eo"
Cohesion: 0.25
Nodes (8): CategorieServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 81 - "DataInitializer.java"
Cohesion: 0.14
Nodes (16): CommandLineRunner, DataInitializer, Component, PasswordEncoder, RequiredArgsConstructor, Slf4j, Categorie, AllArgsConstructor (+8 more)

### Community 83 - "updateStickyColumns"
Cohesion: 0.16
Nodes (15): _addStickyStyle(), clearStickyPositioning(), _getCalculatedZIndex(), _getCellWidths(), _getStickyEndColumnPositions(), _getStickyStartColumnPositions(), Py(), _removeFromStickyColumnReplayQueue() (+7 more)

### Community 84 - "Mf"
Cohesion: 0.29
Nodes (7): _balanceNamespaceList(), collectEnterElement(), createNamespace(), register(), registerTrigger(), si(), Vt()

### Community 85 - "command"
Cohesion: 0.14
Nodes (19): afterFlush(), afterFlushAnimationsDone(), beforeDestroy(), command(), destroy(), finish(), _getPlayer(), Gt() (+11 more)

### Community 86 - "un"
Cohesion: 0.05
Nodes (44): by(), fy(), vy(), yy(), zm(), be(), bs(), ef() (+36 more)

### Community 87 - "chunk-W4AHS3DZ.js"
Cohesion: 0.11
Nodes (14): am(), dc(), dm(), lm(), nm(), om(), tv(), us() (+6 more)

### Community 88 - "SecurityConfig.java"
Cohesion: 0.21
Nodes (13): AuthenticationConfiguration, AuthenticationProvider, Bean, Configuration, CorsConfigurationSource, EnableMethodSecurity, EnableWebSecurity, HttpSecurity (+5 more)

### Community 89 - "JwtService"
Cohesion: 0.15
Nodes (11): Claims, Key, Service, UserDetails, JwtService, AuthServiceImpl, AuthenticationManager, Override (+3 more)

### Community 90 - "CustomUserPrincipal"
Cohesion: 0.19
Nodes (8): GrantedAuthority, CustomUserDetailsService, CustomUserPrincipal, Override, RequiredArgsConstructor, Service, UserDetails, UserDetailsService

### Community 91 - "TransactionUpdateDto"
Cohesion: 0.17
Nodes (9): PutMapping, AllArgsConstructor, Data, NoArgsConstructor, TransactionUpdateDto, TypeTransaction, AFFECTATION, DESAFFECTATION (+1 more)

### Community 92 - "B"
Cohesion: 0.10
Nodes (21): al(), Fd(), hf(), If(), il(), _invokeOnDestroyCallbacks(), jw(), kf() (+13 more)

### Community 93 - "toString"
Cohesion: 0.05
Nodes (46): addHeaderEntry(), appendAll(), applyStyles(), applyToHost(), applyUpdate(), _assignAsyncValidators(), _assignValidators(), constructor() (+38 more)

### Community 94 - "vi"
Cohesion: 0.15
Nodes (11): AI assistant (`assistance/`), Architecture, Auth (two-step, JWT), Commands, Configuration, Domain, Errors, graphify (+3 more)

### Community 95 - "._getCellFromElement"
Cohesion: 0.15
Nodes (3): mw(), Zd(), Qd()

### Community 96 - "constructor"
Cohesion: 0.13
Nodes (19): bh(), bt(), cl(), constructor(), delete(), dt(), Gd(), generateColorScheme() (+11 more)

### Community 97 - "f_"
Cohesion: 0.18
Nodes (16): Bt(), eh(), Ei(), f_(), ih(), lh(), nh(), oh() (+8 more)

### Community 98 - "SchemaAdjustments"
Cohesion: 0.39
Nodes (5): DataSource, JdbcTemplate, SpringBootTest, Test, MigrationDepuisZeroTest

### Community 100 - "j"
Cohesion: 0.12
Nodes (18): openFormDialog(), create(), findTickets(), copy(), Ct(), ic(), j(), jc() (+10 more)

### Community 101 - "n"
Cohesion: 0.12
Nodes (15): At(), bs(), bt(), getParentElement(), match(), matchTransition(), Ms(), normalizePropertyName() (+7 more)

### Community 102 - "get"
Cohesion: 0.10
Nodes (19): clearElementCache(), createRenderer(), Ct(), fs(), get(), has(), hi(), _makeStyleAst() (+11 more)

### Community 105 - "forEach"
Cohesion: 0.23
Nodes (12): clear(), deselect(), _getConcreteValue(), _hasQueuedChanges(), hasValue(), isSelected(), _markSelected(), select() (+4 more)

### Community 106 - "Vs"
Cohesion: 0.15
Nodes (13): Al(), _c(), createElement(), fl(), Gc(), Hs(), ml(), On() (+5 more)

### Community 108 - "ci"
Cohesion: 0.16
Nodes (14): activate(), activateChildRoutes(), activateRoutes(), ci(), deactivateChildRoutes(), deactivateRouteAndItsChildren(), deactivateRoutes(), jb() (+6 more)

### Community 109 - "position"
Cohesion: 0.17
Nodes (12): Mo(), calculateHorizontalAlignment(), calculateHorizontalCaret(), calculateVerticalAlignment(), calculateVerticalCaret(), checkFlip(), determinePlacement(), onWindowResize() (+4 more)

### Community 111 - "Md"
Cohesion: 0.13
Nodes (14): dd(), email(), iD(), Ly(), Md(), minLength(), _registerOnDestroy(), registerOnDisabledChange() (+6 more)

### Community 112 - "De"
Cohesion: 0.12
Nodes (17): appendChild(), bs(), De(), di(), ec(), Fs(), gl(), hr() (+9 more)

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
Cohesion: 0.18
Nodes (11): addHideListeners(), createBoundOptions(), hideTooltip(), ngOnDestroy(), onBlur(), onFocus(), onMouseClick(), onMouseEnter() (+3 more)

### Community 119 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 121 - "CodeGenerationServiceImpl"
Cohesion: 0.36
Nodes (5): CodeGenerationServiceImpl, Override, RequiredArgsConstructor, Service, Slf4j

### Community 123 - "JwtAuthenticationEntryPoint.java"
Cohesion: 0.36
Nodes (7): AuthenticationEntryPoint, AuthenticationException, Component, HttpServletRequest, HttpServletResponse, Override, JwtAuthenticationEntryPoint

### Community 124 - "toString"
Cohesion: 0.04
Nodes (70): clone(), _setValidators(), Ae(), afterRun(), Ar(), at(), br(), cf() (+62 more)

### Community 126 - "wn"
Cohesion: 0.29
Nodes (8): Ce(), Pe(), _r(), vr(), wn(), Xn(), yn(), zn()

### Community 127 - "AuthenticationException"
Cohesion: 0.38
Nodes (3): AuthenticationException, AuthenticationFailedException, InvalidTokenException

### Community 128 - ".processQuestion"
Cohesion: 0.25
Nodes (6): Pattern, AiTrainingLog, AllArgsConstructor, Getter, Service, SqlValidatorService

### Community 129 - "tu"
Cohesion: 0.27
Nodes (10): _executeOnStable(), focusFirstTabbableElement(), focusFirstTabbableElementWhenReady(), focusInitialElement(), focusInitialElementWhenReady(), focusLastTabbableElement(), focusLastTabbableElementWhenReady(), _getFirstTabbableElement() (+2 more)

### Community 130 - "ChangementMotDePasseRequestDto"
Cohesion: 0.53
Nodes (4): ChangementMotDePasseRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 132 - "Logiciel.java"
Cohesion: 0.52
Nodes (6): AllArgsConstructor, Builder, Data, Entity, NoArgsConstructor, Logiciel

### Community 134 - "SpaFallbackController.java"
Cohesion: 0.53
Nodes (4): Controller, Order, RequestMapping, SpaFallbackController

### Community 135 - "NativeQueryService"
Cohesion: 0.53
Nodes (4): EntityManager, Service, Transactional, NativeQueryService

### Community 137 - "TransactionValidationDto"
Cohesion: 0.31
Nodes (4): AllArgsConstructor, Data, NoArgsConstructor, TransactionValidationDto

### Community 139 - "eh"
Cohesion: 0.33
Nodes (6): eh(), kl(), ks(), Qr(), removeChild(), Ss()

### Community 140 - "GroqService"
Cohesion: 0.70
Nodes (4): RestTemplate, GroqService, Logger, Service

### Community 143 - "StatusTransaction"
Cohesion: 0.40
Nodes (4): StatusTransaction, EN_ATTENTE, REJETEE, VALIDEE

### Community 144 - "appendComponent"
Cohesion: 0.33
Nodes (6): appendComponent(), getComponentRootNode(), getRootViewContainer(), getRootViewContainerNode(), projectComponentBindings(), xd()

### Community 145 - "SmartParkApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, Test, SmartParkApplicationTests

### Community 156 - "updatePosition"
Cohesion: 0.06
Nodes (41): addPanelClass(), attach(), _attachBackdrop(), attachComponentPortal(), attachTemplatePortal(), bottom(), centerHorizontally(), centerVertically() (+33 more)

### Community 160 - "toString"
Cohesion: 0.29
Nodes (7): bc(), getColor(), getLinearGradientStops(), mr(), toString(), uo(), Xe()

## Knowledge Gaps
- **51 isolated node(s):** `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE`, `EN_REPARATION`, `MISE_AU_REBUS` (+46 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `i` connect `i` to `chunk-2UMSGIQA.js`, `chunk-JLYFP63R.js`, `main-OWKDLGZI.js`, `chunk-NFK5QI6Q.js`, `dp`, `RefreshTokenRequest`, `.ngOnChanges`, `chunk-XUE47SUA.js`, `e`, `setTimeout`, `.subscribe`, `.remove`, `updateValueAndValidity`, `n`, `a`, `.get`, `.ngOnDestroy`, `.subscribe`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `toString`, `.init`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `e`, `.focus`, `apply`, `.getMonth`, `av`, `attach`, `Qa`, `.updateStickyColumnStyles`, `constructor`, `.constructor`, `update`, `polyfills-B6TNHZQ6.js`, `contains`, `chunk-HTANHSLM.js`, `Fm`, `ze`, `UtilisateurRequestDto`, `.clone`, `ImmobilisationRequestDto`, `updateStickyColumns`, `Mf`, `command`, `un`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `constructor`, `run`, `j`, `n`, `get`, `TypeTransaction`, `._markRadiosForCheck`, `forEach`, `ci`, `position`, `Md`, `._focusActiveCell`, `toString`?**
  _High betweenness centrality (0.185) - this node is a cross-community bridge._
- **Why does `ResourceNotFoundException` connect `UtilisateurServiceImpl` to `TransactionValidationDto`, `TicketServiceImpl.java`, `get`, `chunk-DDEZEMF3.js`, `ErrorResponse`, `ResourceNotFoundException`, `ReportController.java`, `v`, `Immobilisation`, `AgenceServiceImpl`, `TransactionServiceImpl`, `le`, `chunk-YEVIWNIL.js`, `AgenceRepository`, `InterventionServiceImpl`, `UtilisateurResponseDto.java`, `OneTimePassword`, `eo`, `JwtService`, `TransactionUpdateDto`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Why does `e` connect `av` to `i`, `chunk-2UMSGIQA.js`, `tu`, `chunk-JLYFP63R.js`, `chunk-NFK5QI6Q.js`, `bc`, `RefreshTokenRequest`, `dp`, `.ngOnChanges`, `chunk-XUE47SUA.js`, `e`, `setTimeout`, `.subscribe`, `.remove`, `updateValueAndValidity`, `n`, `a`, `.get`, `.ngOnDestroy`, `.subscribe`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `.focus`, `apply`, `.getMonth`, `.updateStickyColumnStyles`, `.constructor`, `chunk-HTANHSLM.js`, `Fm`, `UtilisateurRequestDto`, `.clone`, `Mf`, `command`, `un`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `f_`, `Md`, `._focusActiveCell`, `toString`?**
  _High betweenness centrality (0.019) - this node is a cross-community bridge._
- **Are the 95 inferred relationships involving `i` (e.g. with `.forChild()` and `.forRoot()`) actually correct?**
  _`i` has 95 INFERRED edges - model-reasoned connections that need verification._
- **Are the 116 inferred relationships involving `e` (e.g. with `_addPanelClasses()` and `Af()`) actually correct?**
  _`e` has 116 INFERRED edges - model-reasoned connections that need verification._
- **Are the 118 inferred relationships involving `a()` (e.g. with `aa()` and `activateChildRoutes()`) actually correct?**
  _`a()` has 118 INFERRED edges - model-reasoned connections that need verification._
- **What connects `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE` to the rest of the system?**
  _51 weakly-connected nodes found - possible documentation gaps or missing edges._